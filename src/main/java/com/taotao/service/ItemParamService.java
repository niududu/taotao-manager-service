package com.taotao.service;

import com.taotao.pojo.TbItemParam;
import com.taotao.result.TaotaoResult;

public interface ItemParamService {

	TaotaoResult getItemParamByCid(long cid);

	TaotaoResult insertItemParam(TbItemParam itemParam);

}
