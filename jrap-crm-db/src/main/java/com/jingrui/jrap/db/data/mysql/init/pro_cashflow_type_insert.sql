insert into pro_cashflow_type(cf_type, description, cf_direction, sys_flag, write_off_order, calc_penalty, billing_desc, enabled_flag)
values('0',		'租赁物价款',		'OUTFLOW',		'Y', 			 , 	'N',		'租赁物价款', 	'Y');
insert into pro_cashflow_type(cf_type, description, cf_direction, sys_flag, write_off_order, calc_penalty, billing_desc, enabled_flag)
values('1',		'租金',					'INFLOW',			'Y',		990, 	'Y',		'租金',					'Y');
insert into pro_cashflow_type(cf_type, description, cf_direction, sys_flag, write_off_order, calc_penalty, billing_desc, enabled_flag)
values('10',		'首付款',				'INFLOW',			'Y',		100, 	'N', 		'首付款',				'Y');
insert into pro_cashflow_type(cf_type, description, cf_direction, sys_flag, write_off_order, calc_penalty, billing_desc, enabled_flag)
values('11',		'本金',					'INFLOW',			'Y',		980, 	'Y',   	'本金',					'Y');
insert into pro_cashflow_type(cf_type, description, cf_direction, sys_flag, write_off_order, calc_penalty, billing_desc, enabled_flag)
values('12',		'尾款',					'INFLOW',			'Y',		980, 	'Y',		'尾款',					'Y');
insert into pro_cashflow_type(cf_type, description, cf_direction, sys_flag, write_off_order, calc_penalty, billing_desc, enabled_flag)
values('13',		'留购价款',			'INFLOW',			'Y',		1020, 'N', 		'留购价款',			'Y');
insert into pro_cashflow_type(cf_type, description, cf_direction, sys_flag, write_off_order, calc_penalty, billing_desc, enabled_flag)
values('20',		'利息',					'INFLOW',			'Y',		970, 	'Y',			'利息',					'Y');
insert into pro_cashflow_type(cf_type, description, cf_direction, sys_flag, write_off_order, calc_penalty, billing_desc, enabled_flag)
values('21',		'GPS费用',			'INFLOW',			'Y',		971, 	'N',		'GPS费用',			'Y');
insert into pro_cashflow_type(cf_type, description, cf_direction, sys_flag, write_off_order, calc_penalty, billing_desc, enabled_flag)
values('22',		'停车费',				'INFLOW',			'Y',		972, 	'N',			'停车费',				'Y');
insert into pro_cashflow_type(cf_type, description, cf_direction, sys_flag, write_off_order, calc_penalty, billing_desc, enabled_flag)
values('24',		'服务费',				'INFLOW',			'Y',		973, 'N',			'服务费',		'Y');
insert into pro_cashflow_type(cf_type, description, cf_direction, sys_flag, write_off_order, calc_penalty, billing_desc, enabled_flag)
values('25',		'风险金',				'INFLOW',			'Y',		974, 'N',			'风险金',		'Y');
insert into pro_cashflow_type(cf_type, description, cf_direction, sys_flag, write_off_order, calc_penalty, billing_desc, enabled_flag)
values('26',		'佣金',					'INFLOW',			'Y',		975, 'N',			'佣金',			'Y');
insert into pro_cashflow_type(cf_type, description, cf_direction, sys_flag, write_off_order, calc_penalty, billing_desc, enabled_flag)
values('30',		'产调费',				'INFLOW',			'Y',		200, 'N',			'产调费',			'Y');
insert into pro_cashflow_type(cf_type, description, cf_direction, sys_flag, write_off_order, calc_penalty, billing_desc, enabled_flag)
values('31',		'查档费',				'INFLOW',			'Y',		200, 'N',			'查档费',			'Y');
insert into pro_cashflow_type(cf_type, description, cf_direction, sys_flag, write_off_order, calc_penalty, billing_desc, enabled_flag)
values('32',		'征信费',				'INFLOW',			'Y',		200, 'N',			'征信费',		'Y');
insert into pro_cashflow_type(cf_type, description, cf_direction, sys_flag, write_off_order, calc_penalty, billing_desc, enabled_flag)
values('33',		'抵押费',				'INFLOW',			'Y',		200, 'N',			'抵押费',		'Y');
insert into pro_cashflow_type(cf_type, description, cf_direction, sys_flag, write_off_order, calc_penalty, billing_desc, enabled_flag)
values('51',		'保证金',				'INFLOW',			'Y',		200, 'N',			'保证金',		'Y');
insert into pro_cashflow_type(cf_type, description, cf_direction, sys_flag, write_off_order, calc_penalty, billing_desc, enabled_flag)
values('52',		'保证金退还',		'OUTFLOW',		'Y',  		 ,	'N', 		'保证金退还',		'Y');
insert into pro_cashflow_type(cf_type, description, cf_direction, sys_flag, write_off_order, calc_penalty, billing_desc, enabled_flag)
values('53',		'违章押金',			'INFLOW',			'Y',		200,	'N',		'违章押金',		'Y');
insert into pro_cashflow_type(cf_type, description, cf_direction, sys_flag, write_off_order, calc_penalty, billing_desc, enabled_flag)
values('54',		'违章押金退还',	'OUTFLOW',		'Y',			 ,  'N', 		'违章押金退还',		'Y');
insert into pro_cashflow_type(cf_type, description, cf_direction, sys_flag, write_off_order, calc_penalty, billing_desc, enabled_flag)
values('55',		'保险押金',			'INFLOW',			'Y',		200, 'N',			'保险押金',		'Y');
insert into pro_cashflow_type(cf_type, description, cf_direction, sys_flag, write_off_order, calc_penalty, billing_desc, enabled_flag)
values('56',		'保险押金退还',	'OUTFLOW',		'Y',			 , 'N',  		'保险押金退还',		'Y');
insert into pro_cashflow_type(cf_type, description, cf_direction, sys_flag, write_off_order, calc_penalty, billing_desc, enabled_flag)
values('57',		'行驶证押金',		'INFLOW',			'Y',		200, 'N',			'行驶证押金',		'Y');
insert into pro_cashflow_type(cf_type, description, cf_direction, sys_flag, write_off_order, calc_penalty, billing_desc, enabled_flag)
values('58',		'行驶证押金退还','OUTFLOW',		'Y', 			 , 'N', 		'行驶证押金退还',		'Y');
insert into pro_cashflow_type(cf_type, description, cf_direction, sys_flag, write_off_order, calc_penalty, billing_desc, enabled_flag)
values('80',		'逾期费',				'INFLOW',			'Y',	 1000, 'N',			'罚息费',			'Y');
insert into pro_cashflow_type(cf_type, description, cf_direction, sys_flag, write_off_order, calc_penalty, billing_desc, enabled_flag)
values('81',		'提前违约金',		'INFLOW',			'Y',	 1010, 'N',			'提前违约金',		'Y');