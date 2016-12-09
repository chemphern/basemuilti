package com.ycsys.smartmap.sys.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.ycsys.smartmap.sys.common.utils.JsonMapper;
import com.ycsys.smartmap.sys.common.utils.StreamUtils;
import com.ycsys.smartmap.sys.dao.DictDao;
import com.ycsys.smartmap.sys.dao.DictItemDao;
import com.ycsys.smartmap.sys.entity.Dictionary;
import com.ycsys.smartmap.sys.entity.DictionaryItem;
import com.ycsys.smartmap.sys.entity.PageHelper;
import com.ycsys.smartmap.sys.service.DictService;
import com.ycsys.smartmap.sys.util.DataDictionary;
import org.hibernate.service.spi.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.InputStream;
import java.util.List;
import java.util.Set;

/**
 * 字典service
 * @author lixiaoxin
 * @date 2016年11月17日
 */
@Service("dictService")
public class DictServiceImpl implements DictService {

	private static final Logger logger = LoggerFactory.getLogger(DictServiceImpl.class);

	@Resource
	private DictDao dictDao;

	@Resource
	private DictItemDao dictItemDao;

	@Override
	public List<Dictionary> findAll(PageHelper page) {
		return dictDao.find("from Dictionary order by code",page);
	}

	@Override
	public void saveOrUpdate(Dictionary dictionary) {
		//字典编码不能重复
		long count = 0;
		//新增
		if(dictionary.getId() == null){
			Object [] o = {dictionary.getCode()};
			count = dictDao.count("select count(*) from Dictionary where code = ?",o);
		}else{//修改
			Object [] o = {dictionary.getId(),dictionary.getCode()};
			DataDictionary.refresh(dictionary.getCode(),dictionary.getId());
			count = dictDao.count("select count(*) from Dictionary where id <> ? and code = ?",o);
		}
		if(count > 0) {
			throw new ServiceException("该字典编码已存在！");
		}
		dictDao.saveOrUpdate(dictionary);
	}

	@Override
	public void delete(Dictionary dictionary) {
		Dictionary d = dictDao.get(Dictionary.class,dictionary.getId());
		//如果有子项则删除子项
		if (d.getDictionaryItems() != null && d.getDictionaryItems().size() > 0){
			Set<DictionaryItem> dis = d.getDictionaryItems();
			for(DictionaryItem di : dis){
				dictItemDao.delete(di);
			}
		}
		dictDao.delete(d);
	}

	@Override
	public void deleteItem(DictionaryItem dictionaryItem) {
		dictItemDao.delete(dictionaryItem);
	}

	@Override
	public void saveOrUpdateItem(DictionaryItem item) {
		//子项名称和值都不能重复
		long count = 0;
		Integer dict_id = item.getDictionary().getId();
		if(item.getId() == null){
			Object [] o = {dict_id,item.getName(),item.getValue()};
			count = dictItemDao.count("select count(*) from DictionaryItem where dictionary.id = ? and (name = ? or value = ?)",o);
		}else{
			Object [] o = {item.getId(),dict_id,item.getName(),item.getValue()};
			count = dictItemDao.count("select count(*) from DictionaryItem where id <> ? and dictionary.id = ? and (name = ? or value = ?)",o);
		}
		if(count > 0) {
			throw new ServiceException("子项值或名称已存在！");
		}
		dictItemDao.saveOrUpdate(item);
	}

	@Override
	public Dictionary getById(String dict_id) {
		return dictDao.get(Dictionary.class,Integer.parseInt(dict_id));
	}

	@Override
	public List<DictionaryItem> findItemByDictId(String dictId) {
		return dictItemDao.findAllByDictId(dictId);
	}

	@Override
	public DictionaryItem getItemById(String itemId) {
		return dictItemDao.get(DictionaryItem.class,Integer.parseInt(itemId));
	}

	@Override
	public void initDictionary() throws Exception{
		String filepath = "/data/init/dictionary.json";
		InputStream inputStream = AreaServiceImpl.class.getResource(filepath).openStream();
		if (inputStream != null) {
			String json = StreamUtils.InputStreamTOString(inputStream, "UTF-8");
			List<Dictionary> dList = JsonMapper.getInstance().readValue(json, new TypeReference<List<Dictionary>>() {});
			for(Dictionary dictionary:dList){
				String code = dictionary.getCode();
				long dcount = dictDao.count("select count(*) from Dictionary where code = ?",new Object[]{code});
				if(dcount < 1){
					for(DictionaryItem i :dictionary.getDictionaryItems()){
						i.setDictionary(dictionary);
					};
					dictDao.save(dictionary);
					logger.info("----导入数据字典:" + code  +"----");
				}
			}
		}
		inputStream.close();
	}

}
