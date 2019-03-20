insert into pro_business_type(business_type, description, category, type, usage, cycle, enabled_flag)
values('10', '短期抵押', 'LEASEBACK', 'MORTAGAGE', 'LOAN', 'SHORT', 'Y');

insert into pro_business_type(business_type, description, category, type, usage, cycle, enabled_flag)
values('20', '短期质押', 'LEASEBACK', 'PLEDGE', 'LOAN', 'SHORT', 'Y');

insert into pro_business_type(business_type, description, category, type, usage, cycle, enabled_flag)
values('21', '短期典当', 'LEASEBACK', 'PLEDGE', 'PAWN', 'SHORT', 'Y');

insert into pro_business_type(business_type, description, category, type, usage, cycle, enabled_flag)
values('30', '分期抵押(回租)', 'LEASEBACK', 'MORTAGAGE', 'LEASE', 'LONG', 'Y');

insert into pro_business_type(business_type, description, category, type, usage, cycle, enabled_flag)
values('31', '分期抵押(正租)', 'LEASE', 'MORTAGAGE',  'LEASE', 'LONG', 'Y');

insert into pro_business_type(business_type, description, category, type, usage, cycle, enabled_flag)
values('40', '分期质押(回租)', 'LEASEBACK', 'PLEDGE', 'LEASE', 'LONG', 'Y');

insert into pro_business_type(business_type, description, category, type, usage, cycle, enabled_flag)
values('41', '分期质押(正租)', 'LEASE', 'PLEDGE', 'LEASE', 'LONG', 'Y');

insert into pro_business_type(business_type, description, category, type, usage, cycle, enabled_flag)
values('50', '按天质押', 'LEASEBACK', 'PLEDGE', 'LOAN', 'DAY', 'Y');