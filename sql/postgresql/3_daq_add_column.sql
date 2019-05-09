
alter table pri_role add data_filter_code varchar(45);
comment on column pri_role.data_filter_code is '数据权限过滤规则';


alter table custom_fun_fields add column precision int2;
comment on column custom_fun_fields.precision is 'xiao shu dian bao liu ji wei';
alter table custom_fun_fields alter column child_field type varchar(100) ;

alter table sys_attachment add column pdf_save_path  varchar(500);
comment on column sys_attachment.pdf_save_path is 'pdf文件保存路径';
