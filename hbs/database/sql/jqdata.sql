delete from `t_config_encode` where `ENCODE_TYPE` like 'jq_%';

INSERT INTO `t_config_encode` (`ENCODE_TYPE`,`ENCODE_KEY`,`ENCODE_VALUE`,`ENCODE_DESC`,`IS_VALID`,`SORT_ID`) VALUES ('jq_custOrder_state','cgmanager','','客户订单鉴权','1','');
INSERT INTO `t_config_encode` (`ENCODE_TYPE`,`ENCODE_KEY`,`ENCODE_VALUE`,`ENCODE_DESC`,`IS_VALID`,`SORT_ID`) VALUES ('jq_custOrder_state','cgnormal','20,21','客户订单鉴权','1','');
INSERT INTO `t_config_encode` (`ENCODE_TYPE`,`ENCODE_KEY`,`ENCODE_VALUE`,`ENCODE_DESC`,`IS_VALID`,`SORT_ID`) VALUES ('jq_custOrder_state','cwmanager','33','客户订单鉴权','1','');
INSERT INTO `t_config_encode` (`ENCODE_TYPE`,`ENCODE_KEY`,`ENCODE_VALUE`,`ENCODE_DESC`,`IS_VALID`,`SORT_ID`) VALUES ('jq_custOrder_state','cwnormal','30,31,32','客户订单鉴权','1','');
INSERT INTO `t_config_encode` (`ENCODE_TYPE`,`ENCODE_KEY`,`ENCODE_VALUE`,`ENCODE_DESC`,`IS_VALID`,`SORT_ID`) VALUES ('jq_custOrder_state','scmanager','50','客户订单鉴权','1','');
INSERT INTO `t_config_encode` (`ENCODE_TYPE`,`ENCODE_KEY`,`ENCODE_VALUE`,`ENCODE_DESC`,`IS_VALID`,`SORT_ID`) VALUES ('jq_custOrder_state','scnormal','01,04,05,52,03,39','客户订单鉴权','1','');
INSERT INTO `t_config_encode` (`ENCODE_TYPE`,`ENCODE_KEY`,`ENCODE_VALUE`,`ENCODE_DESC`,`IS_VALID`,`SORT_ID`) VALUES ('jq_cust_state','scmanager','2','客户信息鉴权','1','');
INSERT INTO `t_config_encode` (`ENCODE_TYPE`,`ENCODE_KEY`,`ENCODE_VALUE`,`ENCODE_DESC`,`IS_VALID`,`SORT_ID`) VALUES ('jq_cust_state','scnormal','0,1,3','客户信息鉴权','1','');
INSERT INTO `t_config_encode` (`ENCODE_TYPE`,`ENCODE_KEY`,`ENCODE_VALUE`,`ENCODE_DESC`,`IS_VALID`,`SORT_ID`) VALUES ('jq_vendorOrder_state','cgnormal','01,03,04','供应商订单鉴权','1','');
INSERT INTO `t_config_encode` (`ENCODE_TYPE`,`ENCODE_KEY`,`ENCODE_VALUE`,`ENCODE_DESC`,`IS_VALID`,`SORT_ID`) VALUES ('jq_vendorOrder_state','cknormal','','供应商订单鉴权','1','');
INSERT INTO `t_config_encode` (`ENCODE_TYPE`,`ENCODE_KEY`,`ENCODE_VALUE`,`ENCODE_DESC`,`IS_VALID`,`SORT_ID`) VALUES ('jq_vendorOrder_state','cwnormal','','供应商订单鉴权','1','');
INSERT INTO `t_config_encode` (`ENCODE_TYPE`,`ENCODE_KEY`,`ENCODE_VALUE`,`ENCODE_DESC`,`IS_VALID`,`SORT_ID`) VALUES ('jq_warehouseRec_state','cknormal','01','入库鉴权','1','');
INSERT INTO `t_config_encode` (`ENCODE_TYPE`,`ENCODE_KEY`,`ENCODE_VALUE`,`ENCODE_DESC`,`IS_VALID`,`SORT_ID`) VALUES ('jq_warehouseRec_state','cwnormal','02','入库鉴权','1','');
INSERT INTO `t_config_encode` (`ENCODE_TYPE`,`ENCODE_KEY`,`ENCODE_VALUE`,`ENCODE_DESC`,`IS_VALID`,`SORT_ID`) VALUES ('jq_warehouseSend_state','cknormal','01','出库鉴权','1','');
INSERT INTO `t_config_encode` (`ENCODE_TYPE`,`ENCODE_KEY`,`ENCODE_VALUE`,`ENCODE_DESC`,`IS_VALID`,`SORT_ID`) VALUES ('jq_warehouseSend_state','cwnormal','02','出库鉴权','1','');