package pms.com.domain.common.service;

import java.io.File;
import java.util.List;
import java.util.Map;

import pms.com.domain.common.entity.WordModel;

public interface WordModelServiceInter {
	
	/**
	 * Task : 为 word 模板生成word file 对象文件
	 * @param wordModel 模板枚举
	 * @param params 要填充的参数
	 * @return 成功返回file 对象，反之为null;
	 * date :2017年10月25日
	 * @author libo
	 */
	public File createWordModel(WordModel wordModel, Map<String, String> params,int index);
	
	/**
	 * Task : 压缩文件到压缩文件
	 * @param files 要压缩的文件集合
	 * @return file 压缩文件
	 * date :2017年10月26日
	 * @author libo
	 */
	public File Zip(File[] files, WordModel wordModel);
	
	/**
	 * Task : 为word文档中的第一个表格添加行
	 * @param rowValues 要添加的行的参数
	 * @param file word文档的file对象
	 * @param tableIndex 第几个表格
	 * @param rowIndex 以第几行为模板
	 * @return 成功返回file对象，反之为null
	 * date :2017年10月27日
	 * @author libo
	 */
	public File wordTableAddRow(List<String[]> rowValues, File file, int tableIndex, int rowIndex);
	
	/**
	 * Task : 合并单元格
	 * @param file 文件
	 * @param col 第及列
	 * @param fromRow 从那行开始
	 * @param toRow 到那行
	 * @param tableIndex 第几个表格
	 * @return file
	 * date :2017年10月30日
	 * @author libo
	 */
	public File mergeCellsVertically(File file, int col, int fromRow, int toRow, int tableIndex);
	
}
