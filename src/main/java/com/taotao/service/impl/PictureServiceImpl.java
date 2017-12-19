package com.taotao.service.impl;

import java.io.File;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.aliyun.oss.OSSClient;
import com.taotao.result.PictureResult;
import com.taotao.service.PictureService;
import com.taotao.utils.ExceptionUtil;
import com.taotao.utils.IDUtils;
import com.taotao.utils.OssUtil;

/**
 * 上传图片处理服务实现类
 * <p>Title: PictureService</p>
 * <p>Description: </p>
 * <p>Company: www.itcast.com</p> 
 * @author	入云龙
 * @date	2015年8月15日下午2:59:38
 * @version 1.0
 */
@Service
public class PictureServiceImpl implements PictureService {
	
	private static String endpoint="oss-cn-shenzhen.aliyuncs.com";
	private static String accessKeyId="LTAIfTVYN8w5mmi1";
	private static String accessKeySecret="fDLgyulAXElgA8IZXPuosidGbnvP4f";
	private static String bucketName="niuniu-test";
	

	@Override
	public PictureResult uploadPicture(MultipartFile uploadFile) {
		//判断上传图片是否为空
		if (null == uploadFile || uploadFile.isEmpty()) {
			return PictureResult.error("上传图片为空");
		}
		//取文件扩展名
				String originalFilename = uploadFile.getOriginalFilename();
				String ext = originalFilename.substring(originalFilename.lastIndexOf("."));
				 String fileKey = IDUtils.genImageName();
		try {
			OSSClient ossClient=new OSSClient(endpoint, accessKeyId, accessKeySecret);
			OssUtil.bucketReadPublic(ossClient, bucketName);
			OssUtil.upload(ossClient, bucketName, fileKey+ext,uploadFile.getInputStream());
		} catch (Exception e) {
			e.printStackTrace();
			return PictureResult.error(ExceptionUtil.getStackTrace(e));
		}
		//返回结果，生成一个可以访问到图片的url返回
		String url="http://"+bucketName+"."+endpoint+"/"+fileKey+ext;
		return PictureResult.ok(url);
	}

}
