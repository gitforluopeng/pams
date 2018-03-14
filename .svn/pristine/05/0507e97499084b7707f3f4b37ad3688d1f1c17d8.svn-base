package pms.com.domain.common.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STMerge;
import org.springframework.stereotype.Service;

import pms.com.domain.common.entity.WordModel;
import pms.com.domain.common.service.WordModelServiceInter;

@Service
public class WordModelServer implements WordModelServiceInter{

	@Override
	public File createWordModel(WordModel wordModel, Map<String, String> params, int index) {
		String path = Thread.currentThread().getContextClassLoader().getResource("").toString();
		path = path.substring(0, path.indexOf("WEB-INF/")+8);
		path = path.replace("file:/", "");
		path = path + "//templet//";
		File file = new File(path+wordModel.getPath());
		InputStream in = null;
		OutputStream out = null;
		XWPFDocument document = null;
		try {
			in = new FileInputStream(file);
			File newFile = new File(index+wordModel.name);
			document = new XWPFDocument(in);
			markComb(document);
			List<XWPFParagraph> paragraphs = document.getParagraphs();
			for(int i = 0; i < paragraphs.size(); i++){
				boolean status = false;
				XWPFParagraph paragraph = paragraphs.get(i);
				String text = paragraph.getText();
				Matcher matcher = getMatcher(text);
				if(matcher.find()){
					List<XWPFRun> runs = paragraph.getRuns();
					for(XWPFRun run: runs){
						String runText = run.text();
						System.out.println(runText+": runText");
						while((matcher = getMatcher(runText)).find()){
							System.out.println(params.get(matcher.group(1))+"   :  param");
							status = true;
							runText = matcher.replaceFirst(params.get(matcher.group(1)));
						}
						if(status){
							run.setText(runText,0);
						}
					}
				}
			}
			insertTableParam(params, document);
			out = new FileOutputStream(newFile);
			document.write(out);
			out.flush();
			return newFile;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			try {
				if(out != null){
					out.close();
				}
				if(in != null){
					in.close();
				}
				if(document != null){
					document.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		
		
		return null;
	}
	
	private void insertTableParam(Map<String, String> params, XWPFDocument document){
		
		Iterator<XWPFTable> tables = document.getTablesIterator();
		while(tables.hasNext()){
			XWPFTable table = tables.next();
			int runCount = table.getNumberOfRows();
			for(int i = 0; i < runCount; i++){
				XWPFTableRow row = table.getRow(i);
				List<XWPFTableCell> cells = row.getTableCells();
				for(int j = 0; j < cells.size(); j++){
					XWPFTableCell cell = cells.get(j);
					String cellText = cell.getText();
					Matcher matcher = getMatcher(cellText);
					while((matcher = getMatcher(cellText)).find()){
						System.out.println(params.get(matcher.group(1))+" : "+matcher.group(1));
						cellText = matcher.replaceFirst(params.get(matcher.group(1)));
					}
					cell.removeParagraph(0);
					cell.setText(cellText.trim());
				}
			}
		}
	}
	
	
	
	private Matcher getMatcher(String paramName){
		Pattern pattern = Pattern.compile("\\$\\{(.+?)\\}", Pattern.CASE_INSENSITIVE);
		return pattern.matcher(paramName);
	}
	
	public void markComb(XWPFDocument document) {
		boolean markBeginMissing = false; 
		int markBeginRunIndex = -1;
		StringBuffer markKeySB = null; 
		List<XWPFParagraph> paragraphList = document.getParagraphs();
		List<XWPFRun> runList = null;
		XWPFParagraph paragraph = null;
		XWPFRun markRun = null;
		for (int i = 0; i < paragraphList.size(); i++) {
			paragraph = paragraphList.get(i);
			runList = paragraph.getRuns();
			for (int p = 0; p < runList.size(); p++) {
				markRun = runList.get(p);
				String runTempText = markRun.toString();
				if (!"".equals(runTempText) && runTempText != null) {
					int markEndStrIndex;
					int markBeginStrIndex;
					if (markBeginRunIndex > -1) {
						markEndStrIndex = runTempText.lastIndexOf("}");
						if (markEndStrIndex > -1) {
							if (runTempText.length() > ++markEndStrIndex) {
								markKeySB.append(runTempText.substring(0, markEndStrIndex));
								markRun.setText(runTempText.substring(markEndStrIndex), 0);
							} else {
								markKeySB.append(runTempText);
								paragraph.removeRun(p--);
							}
							markRun = runList.get(markBeginRunIndex);
							markRun.setText(markKeySB.toString().trim(), 0);
							markBeginRunIndex = -1;
						} else {
							paragraph.removeRun(p--);
							markKeySB.append(runTempText);
						}
					} else {
						markBeginStrIndex = runTempText.lastIndexOf("${");
						if (markBeginStrIndex > -1) {
							markEndStrIndex = runTempText.substring(markBeginStrIndex).lastIndexOf("}");
							if (markEndStrIndex < 0) {
								markKeySB = new StringBuffer(runTempText);
								markBeginRunIndex = p;
							}
						} else if (markBeginMissing && runTempText.startsWith("{")) {
							markEndStrIndex = runTempText.lastIndexOf("}");
							markBeginStrIndex = runTempText.lastIndexOf("${");
							if (markEndStrIndex < 0 || markEndStrIndex < markBeginStrIndex) {
								markKeySB = new StringBuffer("$");
								markKeySB.append(runTempText);
								paragraph.removeRun(p--);
								markBeginRunIndex = p;
							}
						}
					}
					markBeginMissing = runTempText.endsWith("$");
				}
			}
		}
	}
	

	@Override
	public File Zip(File[] files, WordModel wordModel) {
		String zipName = wordModel.getName().substring(0, wordModel.getName().lastIndexOf('.')-1);
		File newFile = new File(zipName+".zip");
		OutputStream out = null;
		try {
			out = new FileOutputStream(newFile);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		ZipOutputStream zipOut = new ZipOutputStream(out);
		InputStream in = null;
		int i = 1;
		for(File file: files){
			try {
				ZipEntry zipEntry = new ZipEntry(zipName+"-"+(i++)+".docx");
				zipOut.putNextEntry(zipEntry);
				in = new FileInputStream(file);
				byte[] buffer = new byte[1024];
				int index = -1;
				while((index = in.read(buffer)) != -1){
					zipOut.write(buffer, 0, index);
					zipOut.flush();
				}
				zipOut.closeEntry();
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try {
			zipOut.finish();
			zipOut.close();
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return newFile;
	}

	@Override
	public File wordTableAddRow(List<String[]> rowValues, File file, int tableIndex, int rowIndex) {
		OutputStream out = null;
		InputStream in = null;
		XWPFDocument document = null;
		File newFile = null;
		try {
			newFile = new File(UUID.randomUUID().toString()+"-"+file.getName());
			out = new FileOutputStream(newFile);
			in = new FileInputStream(file);
			document = new XWPFDocument(in);
			Iterator<XWPFTable> tables = document.getTablesIterator();
			int index = 0;
			while(tables.hasNext()){
				if(index == (tableIndex - 1)){
					XWPFTable table = tables.next();
					XWPFTableRow sourcesRow = table.getRow(rowIndex - 1);
					for(int i = 0; i < rowValues.size(); i++){
						String[] values = rowValues.get(i);
						XWPFTableRow row = copyRow(sourcesRow,table.insertNewTableRow(rowIndex++));
						List<XWPFTableCell> cells = row.getTableCells();
						for(int j = 0; j < values.length; j++){
							XWPFTableCell cell = null;
							if(j >= cells.size()){
								cell = row.addNewTableCell();
							}else {
								cell = cells.get(j);
							}
							cell.setText(values[j]);
						}
					}
				}
			}
			document.write(out);
			out.flush();
			
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			try {
				if(document != null){
					document.close();
				}
				if(in != null){
					in.close();
				}
				if(out != null){
					out.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		
		return newFile;
	}
	
	public XWPFTableRow copyRow(XWPFTableRow sourceRow, XWPFTableRow copyRow){
		copyRow.getCtRow().setTrPr(sourceRow.getCtRow().getTrPr());
		List<XWPFTableCell> cells = sourceRow.getTableCells();
		for(XWPFTableCell cell: cells){
			XWPFTableCell newCell = copyRow.createCell();
			newCell.getCTTc().setTcPr(cell.getCTTc().getTcPr());
			newCell.setVerticalAlignment(cell.getVerticalAlignment());
			newCell.setColor(cell.getColor());
		}
		return copyRow;
	}
	
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
	public File mergeCellsVertically(File file, int col, int fromRow, int toRow, int tableIndex) {
		col = col - 1;
		fromRow = fromRow - 1;
		toRow = toRow - 1;
		InputStream in = null;
		XWPFTable table = null;
		OutputStream out = null;
		XWPFDocument document = null;
		File newFile = new File(UUID.randomUUID()+file.getName());
		try {
			in = new FileInputStream(file);
			out = new FileOutputStream(newFile);
			document = new XWPFDocument(in);
			List<XWPFTable> tables = document.getTables();
			if(tables.size() <= tableIndex){
				table = tables.get(tableIndex - 1);
			}else {
				throw new RuntimeException("没有找到第"+tableIndex+"表格");
			}
			for (int rowIndex = fromRow; rowIndex <= toRow; rowIndex++) {  
				XWPFTableCell cell = table.getRow(rowIndex).getCell(col);  
				if ( rowIndex == fromRow ) {  
					cell.getCTTc().addNewTcPr().addNewVMerge().setVal(STMerge.RESTART);  
				} else {  
					cell.getCTTc().addNewTcPr().addNewVMerge().setVal(STMerge.CONTINUE);  
				}  
			} 
			document.write(out);
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			try {
				if(out != null){
					out.close();
				}
				if(document != null){
					document.close();
				}
				if(in != null ){
					in.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		
		return newFile;
	}
}
