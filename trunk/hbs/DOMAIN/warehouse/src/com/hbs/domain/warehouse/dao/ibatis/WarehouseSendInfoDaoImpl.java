package com.hbs.domain.warehouse.dao.ibatis;

import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import com.hbs.domain.warehouse.pojo.WarehouseSendInfo;
import com.hbs.domain.warehouse.dao.WarehouseSendInfoDao;

/**
 * WarehouseSendInfoDao�ӿ�ʵ����.
 * @author hbs
 *
 */
public class WarehouseSendInfoDaoImpl extends SqlMapClientDaoSupport implements WarehouseSendInfoDao
{
    /**
     * logger.
     */
    private static final Logger logger = Logger.getLogger(WarehouseSendInfoDaoImpl.class);

    
    
    /**
     * insert.
     * @param warehouseSendInfo warehouseSendInfo
     * @return id
     * @throws DataAccessException DataAccessException
     */
    public void insertWarehouseSendInfo(WarehouseSendInfo warehouseSendInfo) throws DataAccessException {
		if (logger.isDebugEnabled()) {
    		logger.debug("����insertWarehouseSendInfo(WarehouseSendInfo), �������[" + warehouseSendInfo + "]");
    	}
        
    	getSqlMapClientTemplate().insert("WarehouseSendInfo_insertWarehouseSendInfo", warehouseSendInfo);
		if (logger.isDebugEnabled()) {
    		logger.debug("�뿪insertWarehouseSendInfo(WarehouseSendInfo), ����");
		}
    	
    }

    /**
     * delete.
     * @param warehouseSendInfo warehouseSendInfo
     * @throws DataAccessException DataAccessException
     */
    public void deleteWarehouseSendInfo(String pk)throws DataAccessException {
		if (logger.isDebugEnabled()) {
    		logger.debug("����deleteWarehouseSendInfo(String), �������[" + pk + "]");
		}
        getSqlMapClientTemplate().update("WarehouseSendInfo_deleteWarehouseSendInfo", pk);
		if (logger.isDebugEnabled()) {
    		logger.debug("�뿪deleteWarehouseSendInfo(String)");
		}
    }
    
    /**
     * update.
     * @param warehouseSendInfo warehouseSendInfo
     * @throws DataAccessException DataAccessException
     */
    public void updateWarehouseSendInfo(WarehouseSendInfo warehouseSendInfo) throws DataAccessException {
		if (logger.isDebugEnabled()) {
    		logger.debug("����updateWarehouseSendInfo(WarehouseSendInfo), �������[" + warehouseSendInfo + "]");
		}
    	getSqlMapClientTemplate().update("WarehouseSendInfo_updateWarehouseSendInfo", warehouseSendInfo);
		if (logger.isDebugEnabled()) {
    		logger.debug("�뿪updateWarehouseSendInfo(WarehouseSendInfo)");
		}
    }
    
    /**
     * find.
     * @param id id
     * @return warehouseSendInfo
     * @throws DataAccessException DataAccessException
     */
    public WarehouseSendInfo findWarehouseSendInfo(WarehouseSendInfo whSendInfo) throws DataAccessException {
		if (logger.isDebugEnabled()) {
        	logger.debug("����findWarehouseSendInfo(WarehouseSendInfo), �������[" + whSendInfo + "]");
		}
        WarehouseSendInfo warehouseSendInfo = (WarehouseSendInfo) getSqlMapClientTemplate().queryForObject("WarehouseSendInfo_findWarehouseSendInfo", whSendInfo);
		if (logger.isDebugEnabled()) {
        	logger.debug("�뿪findWarehouseSendInfo(WarehouseSendInfo), ����[" + warehouseSendInfo + "]");
		}
        return warehouseSendInfo;
    }
    
    /**
     * list.
     * @param warehouseSendInfo warehouseSendInfo
     * @return warehouseSendInfo list
     * @throws DataAccessException DataAccessException
     */
    public List listWarehouseSendInfo(WarehouseSendInfo warehouseSendInfo) throws DataAccessException {
		if (logger.isDebugEnabled()) {
        	logger.debug("����listWarehouseSendInfo(WarehouseSendInfo), �������[" + warehouseSendInfo + "]");
		}
        List list = getSqlMapClientTemplate().queryForList("WarehouseSendInfo_listWarehouseSendInfo", warehouseSendInfo);
		if (logger.isDebugEnabled()) {
        	logger.debug("�뿪listWarehouseSendInfo(WarehouseSendInfo), ����[" + list + "]");
		}
        return list;
    }  
    
    /**
     * listCount.
     * @param warehouseSendInfo warehouseSendInfo
     * @return list count
     * @throws DataAccessException DataAccessException
     */
    public Integer listWarehouseSendInfoCount(WarehouseSendInfo warehouseSendInfo) throws DataAccessException {
		if (logger.isDebugEnabled()) {
        	logger.debug("����listWarehouseSendInfoCount(WarehouseSendInfo), �������[" + warehouseSendInfo + "]");
		}
        Integer count = (Integer)getSqlMapClientTemplate().queryForObject("WarehouseSendInfo_listWarehouseSendInfoCount", warehouseSendInfo);
		if (logger.isDebugEnabled()) {
        	logger.debug("�뿪listWarehouseSendInfoCount(WarehouseSendInfo), ����[" + count + "]");
		}
        return count;
    }  
}