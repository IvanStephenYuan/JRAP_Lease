//从附件列表中删除此附件
function addFileToList(file){
		var fileHtml="";
		fileHtml='<tr id="'+file.fileId+'">';
		fileHtml+='<td>'+file.fileName+'</td>';
		fileHtml+=' <td>'+file.fileSize+'</td>';
		fileHtml+=' <td>'+file.uploadDate+'</td>';
		fileHtml+='<td><span><a  target="_blank"  href="'+$("#contextPath").val()+'/sys/attach/file/view?fileId='+file.fileId+'">'+$('#pic_btn_view').val()+'</a>'
		fileHtml+='</span>|<span><a href="javascript:void(0)" onclick="rmfile(\''+file.fileId+'\')">'+$('#pic_btn_del').val()+'</a></span></td>';
		fileHtml+='</tr>';
		$("#attach_list_body").append(fileHtml);
	}

jQuery(function() {
    var $ = jQuery,    // just in case. Make sure it's not an other libaray.
        $wrap = $('#uploader'),
        // 图片容器
        $queue = $('<ul class="filelist"></ul>')
            .appendTo( $wrap.find('.queueList') ),
        // 状态栏，包括进度和控制按钮
        $statusBar = $wrap.find('.statusBar'),
        // 文件总体选择信息。
        $info = $statusBar.find('.info'),
        // 上传按钮
        $upload = $wrap.find('.uploadBtn'),
        // 没选择文件之前的内容。
        $placeHolder = $wrap.find('.placeholder'),
        // 总体进度条
        $progress = $statusBar.find('.progress').hide(),
        // 添加的文件数量
        fileCount = 0,
        // 添加的文件总大小
        fileSize = 0,
        // 优化retina, 在retina下这个值是2
        ratio = window.devicePixelRatio || 1,
        // 缩略图大小
        thumbnailWidth = 110 * ratio,
        thumbnailHeight = 110 * ratio,
        // 可能有pedding, ready, uploading, confirm, done.
        state = 'pedding',
        // 所有文件的进度信息，key为file id
        percentages = {},
        supportTransition = (function(){
            var s = document.createElement('p').style,
                r = 'transition' in s ||
                      'WebkitTransition' in s ||
                      'MozTransition' in s ||
                      'msTransition' in s ||
                      'OTransition' in s;
            s = null;
            return r;
        })(),

        // WebUploader实例
        uploader;

    if ( !WebUploader.Uploader.support() ) {
        alert( $('#upload_not_support').val());
        throw new Error($('#upload_not_support').val());
    }

    // 实例化
    uploader = WebUploader.create({
        pick: {
            id: '#filePicker',
            label: $('#btn_choose_file').val()
        },
        dnd: '#uploader .queueList',
        paste: document.body,

        accept: null,

        // swf文件路径
        swf: BASE_URL + '/js/Uploader.swf',

        disableGlobalDnd: true,

        chunked: false,
         //服务器地址
//        server: 'http://localhost:8080/WebUploader/file/upload.do',
        server: $("#contextPath").val()+'/sys/attach/upload',
        fileNumLimit: 5,
        fileSizeLimit: undefined,    
        fileSingleSizeLimit: undefined,
        formData:{
        		sourceType:$("#sourceType").val(),
        		sourceKey:$("#sourceKey").val()
        		}
    });

    // 添加“添加文件”的按钮，
    uploader.addButton({
        id: '#filePicker2',
        label: $('#btn_con_add').val()
    });

    // 当有文件添加进来时执行，负责view的创建
    function addFile( file ) {
        var $li = $( '<li id="' + file.id + '">' +
                '<p class="title">' + file.name + '</p>' +
                '<p class="imgWrap"></p>'+
                '<p class="progress"><span></span></p>' +
                '</li>' ),

            $btns = $('<div class="file-panel">' +
                '<span class="cancel">'+$('#queue_btn_del').val()+'</span>' +
                '<span class="rotateRight">'+$('#btn_rotateright').val()+'</span>' +
                '<span class="rotateLeft">'+$('#btn_rotateleft').val()+'</span></div>').appendTo( $li ),
            $prgress = $li.find('p.progress span'),
            $wrap = $li.find( 'p.imgWrap' ),
            $info = $('<p class="error"></p>'),

            showError = function( code ) {
                switch( code ) {
                    case 'exceed_size':
                        text = $('#single_file_size_max_error').val();
                        break;

                    case 'interrupt':
                        text = $('#upload_interrupt').val();
                        break;
                    case 'TYPEORKEY_EMPTY':
                        text = $('#typeorkey_empty').val();
                        break;
                    case 'FILE_EMPTY_ERROR':
                        text = $('#file_empty_error').val();
                        break;
                    case 'FILE_DISALLOWD_ERROR':
                        text = $('#file_disallowd_error').val();
                        break;
                    case 'SINGLE_FILE_SIZE_MAX_ERROR':
                        text = $('#single_file_size_max_error').val();
                        break;
                    case 'DATABASE_ERROR':
                        text = $('#database_error').val();
                        break;
                    default:
                        text = $('#default_error').val();
                        break;
                }

                $info.text( text ).appendTo( $li );
            };

        if ( file.getStatus() === 'invalid' ) {
            showError( file.statusText );
        } else {
            $wrap.text( $('#pic_preview').val() );
            uploader.makeThumb( file, function( error, src ) {
                if ( error ) {
                    $wrap.text( $('#pic_cannot_preview').val() );
                    return;
                }

                var img = $('<img src="'+src+'">');
                $wrap.empty().append( img );
            }, thumbnailWidth, thumbnailHeight );

            percentages[ file.id ] = [ file.size, 0 ];
            file.rotation = 0;
        }

        file.on('statuschange', function( cur, prev ) {
            if ( prev === 'progress' ) {
                $prgress.hide().width(0);
            } else if ( prev === 'queued' ) {
                $li.off( 'mouseenter mouseleave' );
                $btns.css("height","0px");
            			}
            if(cur === 'complete'){
            	 $btns.css("height","30px");
            			}

            // 成功
            if ( cur === 'error' || cur === 'invalid' ) {
               showError( file.statusText );
                percentages[ file.id ][ 1 ] = 1;
            } else if ( cur === 'interrupt' ) {
                showError( 'interrupt' );
            } else if ( cur === 'queued' ) {
                percentages[ file.id ][ 1 ] = 0;
            } else if ( cur === 'progress' ) {
                $info.remove();
                $prgress.css('display', 'block');
            } else if ( cur === 'success' ) {
                $li.append( '<span class="success"></span>' );
            }else if(cur == 'TYPEORKEY_EMPTY' && prev == 'complete'){
            	  showError('TYPEORKEY_EMPTY');//上传的时候sourceType和sourceKey没传
            }else if(cur == 'FILE_DISALLOWD_ERROR' && prev == 'complete'){
            	 showError('FILE_DISALLOWD_ERROR');
            }else if(cur == 'FILE_EMPTY_ERROR' && prev == 'complete'){
            	 showError('FILE_EMPTY_ERROR');
            }else if(cur == 'SINGLE_FILE_SIZE_MAX' && prev =='complete'){
            	 showError('SINGLE_FILE_SIZE_MAX');
            }else if(cur != 'queued' && cur != 'process' && cur != 'complete'){
             	showError(cur);
            }

            $li.removeClass( 'state-' + prev ).addClass( 'state-' + cur );
        });

        $li.on( 'mouseenter', function() {
            $btns.stop().animate({height: 30});
        });

        $li.on( 'mouseleave', function() {
            $btns.stop().animate({height: 0});
        });

        $btns.on( 'click', 'span', function() {
            var index = $(this).index(),
                deg;

            switch ( index ) {
                case 0:
                    uploader.removeFile( file );
                    return;

                case 1:
                    file.rotation += 90;
                    break;

                case 2:
                    file.rotation -= 90;
                    break;
            }

            if ( supportTransition ) {
                deg = 'rotate(' + file.rotation + 'deg)';
                $wrap.css({
                    '-webkit-transform': deg,
                    '-mos-transform': deg,
                    '-o-transform': deg,
                    'transform': deg
                });
            } else {
                $wrap.css( 'filter', 'progid:DXImageTransform.Microsoft.BasicImage(rotation='+ (~~((file.rotation/90)%4 + 4)%4) +')');
                }


        });

        $li.appendTo( $queue );
    }

    // 负责view的销毁
    function removeFile( file ) {
        var $li = $('#'+file.id);
        delete percentages[ file.id ];
        updateTotalProgress();
        $li.off().find('.file-panel').off().end().remove();
    }

    function updateTotalProgress() {
        var loaded = 0,
            total = 0,
            spans = $progress.children(),
            percent;

        $.each( percentages, function( k, v ) {
            total += v[ 0 ];
            loaded += v[ 0 ] * v[ 1 ];
        } );

        percent = total ? loaded / total : 0;

        spans.eq( 0 ).text( Math.round( percent * 100 ) + '%' );
        spans.eq( 1 ).css( 'width', Math.round( percent * 100 ) + '%' );
        updateStatus();
    }

    function updateStatus() {
        var text = '', stats;

        if ( state === 'ready' ) {
        	
        } else if ( state === 'confirm' ) {
            stats = uploader.getStats();
        } else {
            stats = uploader.getStats();
        	}
        $info.html(text);
				//不管成不成功，都继续可以上传
		    $upload.removeClass('disabled');
		    $('#filePicker2').removeClass( 'element-invisible' );
    }

    //设置状态
    function setState( val ) {
        var file, stats;

        if ( val === state ) {
            return;
        	}

        $upload.removeClass( 'state-' + state );
        $upload.addClass( 'state-' + val );
        state = val;

        switch ( state ) {
            case 'pedding':
                $placeHolder.removeClass( 'element-invisible' );
                $queue.parent().removeClass('filled');
                $queue.hide();
                $statusBar.addClass( 'element-invisible' );
                uploader.refresh();
                break;

            case 'ready':
                $placeHolder.addClass( 'element-invisible' );
                $( '#filePicker2' ).removeClass( 'element-invisible');
                $queue.parent().addClass('filled');
                $queue.show();
                $statusBar.removeClass('element-invisible');
                uploader.refresh();
                break;

            case 'uploading':
                $( '#filePicker2' ).addClass( 'element-invisible' );
                $progress.show();
                $upload.text( $('#pause_upload').val() );
                break;

            case 'paused':
                $progress.show();
                $upload.text($('#resume_upload').val());
                break;

            case 'confirm':
                $progress.hide();
                $upload.text( $('#start_upload').val() ).addClass( 'disabled' );

                stats = uploader.getStats();
                if ( stats.successNum && !stats.uploadFailNum ) {
                    setState( 'finish' );
                    return;
                			}
                break;
            case 'finish':
                stats = uploader.getStats();
                if ( stats.successNum ) {
                							
                } else {
                    // 没有成功的图片，重设
                    state = 'done';
                    location.reload();
                				}
                break;
        }

        updateStatus();
    }

    uploader.onUploadProgress = function( file, percentage ) {
        var $li = $('#'+file.id),
            $percent = $li.find('.progress span');

        $percent.css( 'width', percentage * 100 + '%' );
        percentages[ file.id ][ 1 ] = percentage;
        updateTotalProgress();
    };

    uploader.onFileQueued = function( file ) {
        fileCount++;
        fileSize += file.size;

        if ( fileCount === 1 ) {
            $placeHolder.addClass( 'element-invisible' );
            $statusBar.show();
        }

        addFile( file );
        setState( 'ready' );
        updateTotalProgress();
    };

    uploader.onFileDequeued = function( file ) {
        fileCount--;
        fileSize -= file.size;

        if ( !fileCount ) {
            setState( 'pedding' );
        }

        removeFile(file);
        updateTotalProgress();

    };

    uploader.on( 'all', function( type ) {
        var stats;
        switch( type ) {
            case 'uploadFinished':
                setState( 'confirm' );
                break;

            case 'startUpload':
                setState( 'uploading' );
                break;

            case 'stopUpload':
                setState( 'paused' );
                break;

        }
    });

    uploader.onError = function( code ) {
    	if(code  == 'Q_EXCEED_NUM_LIMIT'){
    		alert($('#queue_num_not_allowed').val());
    	}else{
    		alert( 'Error: ' + code );
    	}
    };

    $upload.on('click', function() {
        if ( $(this).hasClass( 'disabled' ) ) {
            return false;
        }

        if ( state === 'ready' ) {
            uploader.upload();
        } else if ( state === 'paused' ) {
            uploader.upload();
        } else if ( state === 'uploading' ) {
            uploader.stop();
        }
    });
    
    

    $info.on( 'click', '.retry', function() {
        uploader.retry();
    	} );

    	//如果忽略，就删除
    $info.on( 'click', '.ignore', function() {
    	var files=uploader.getFiles('error');
    	for (var i = 0; i < files.length; i++) {
    		  uploader.removeFile( files[i] );
			}
        
    } );

    $upload.addClass( 'state-' + state );
    updateTotalProgress();
    	//文件不能上传触发此事件
    uploader.onUploadError=function(file,reason){
    			alert(file.id+$('#default_error').val()+reason);
    	}
    
    	//如果文件上传了，不管成不成功，触发  
    uploader.onUploadSuccess=function(file,response){
    	if(response.message == 'UPLOAD_SUCCESS' ){
    			file.setStatus('success'); 
    			addFileToList(response.file);
    		}else{
    			file.setStatus(response.info); 
    		}
    	}
});