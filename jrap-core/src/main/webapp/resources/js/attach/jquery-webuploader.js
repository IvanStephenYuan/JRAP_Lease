//create by xiaohua 2015 13:02    
$.createUploader = function(opt) {
	var $ = jQuery, state = 'pending', uploader, fileNum = 0, percent = 0, fileIndex = 0, BASE_URL = '/webuploader';

	var opt = $.extend({
		resize : true,
		auto : true,
		swf : BASE_URL + '/js/Uploader.swf',
		contextPath : "/dsis",
		uploaderPath : "/sys/attach/upload",
		accepts : null,
		fileNumLimit : undefined,
		fileSizeLimit : undefined, // 16 M
		fileSingleSizeLimit : undefined, // 2 M
		picker : 'picker',
		uploaderProgressar : 'uploader-progress-bar',
		chunked : false,
		chunkSize : 5242880,
		threads : 1,
		fileVal : 'file',
		chunkRetry : 3
	}, opt || {});

	if ($("#" + opt.picker).length <= 0) {
		throw new Error("upload picker is not correct!");
	}

	uploader = WebUploader.create({
		resize : opt.resize,
		chunked : opt.chunked,
		chunkSize : opt.chunSize,
		threads : opt.threads,
		fileVal : opt.fileVal,
		chunkRetry : opt.chunkRetry,
		auto : opt.auto,
		swf : BASE_URL + '/js/Uploader.swf',
		server : opt.contextPath + opt.uploadPath,
		pick : '#' + opt.picker,
		accept : opt.accepts,
		fileNumLimit : opt.fileNumlimit,
		fileSizeLimit : opt.fileSizeLimit, // 16 M
		fileSingleSizeLimit : opt.fileSingleSizeLimit
	// 2 M
	});

	uploader.on('fileQueued', function(file) {
		fileNum++;
		fileIndex++;
	});

	// 文件上传过程中创建进度条实时显示。
	uploader.on('uploadProgress', function(file, percentage) {
		fileIndex--;
		if ($("#" + opt.uploaderProgressar).length > 0) {
			//上传一次单会调用两次，很奇怪
			if (fileIndex >= 0) {
				$percent = $("#" + opt.uploaderProgressar);
				percent = percentage * 100 / fileNum + percent;
				$percent.css('width', percent + '%');
				$percent.find('span').text(percent + '%' + ' 完成');
			}
		}
		if (fileIndex <= 0) {
			fileNum = 0;
			percent = 0;
		}
	});

	uploader.on('uploadSuccess', function(file, response) {
		if (opt.uploadSuccess) {
			var isDelete = false;
			isDelete = opt.uploadSuccess(file, response);
			if (isDelete) {
				uploader.removeFile(file);
			}
		}
	});

	uploader.on('uploadError', function(file) {
		if (opt.uploadError) {
			opt.uploadError(file);
		}
		uploader.removeFile(file);

	});

	uploader.on('uploadComplete', function(file) {
		/*
		inited 初始状态
		queued 已经进入队列, 等待上传
		progress 上传中
		complete 上传完成。
		error 上传出错，可重试
		interrupt 上传中断，可续传。
		invalid 文件不合格，不能重试上传。会自动从队列中移除。
		cancelled 文件被移除。
		 */
		if (opt.uploadComplete) {
			opt.uploadComplete(file);
		}
	});

	/*
	 当validate不通过时，会以派送错误事件的形式通知调用者。通过upload.on('error', handler)可以捕获到此类错误，目前有以下错误会在特定的情况下派送错来。

	 Q_EXCEED_NUM_LIMIT 在设置了fileNumLimit且尝试给uploader添加的文件数量超出这个值时派送。
	 Q_EXCEED_SIZE_LIMIT 在设置了Q_EXCEED_SIZE_LIMIT且尝试给uploader添加的文件总大小超出这个值时派送。
	 Q_TYPE_DENIED 当文件类型不满足时触发。。

	 */
	uploader.onError = function(code) {
		//typeof(eval(funcName)) == "function"
		if (opt.validateError) {
			validateError(code);
		}
	};

	uploader.on('all', function(type) {
		if (type === 'startUpload') {
			state = 'uploading';
		} else if (type === 'stopUpload') {
			state = 'paused';
		} else if (type === 'uploadFinished') {
			state = 'done';
		}
	});
	return uploader;
};
