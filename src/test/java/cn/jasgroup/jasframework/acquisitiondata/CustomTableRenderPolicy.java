package cn.jasgroup.jasframework.acquisitiondata;

import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;

import com.deepoove.poi.NiceXWPFDocument;
import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.data.MiniTableRenderData;
import com.deepoove.poi.data.RowRenderData;
import com.deepoove.poi.policy.AbstractRenderPolicy;
import com.deepoove.poi.policy.MiniTableRenderPolicy;
import com.deepoove.poi.render.RenderContext;
import com.deepoove.poi.template.run.RunTemplate;
import com.deepoove.poi.util.TableTools;

/**
 * @description 自定义表格渲染
 * @author zhangyi
 * @date 2019年8月13日下午2:01:34
 * @version V1.0
 * @since JDK 1.80
 */

public class CustomTableRenderPolicy extends AbstractRenderPolicy<Object> {

	@Override
	protected void afterRender(RenderContext context) {
		// 清空模板标签所在段落
		clearPlaceholder(context, true);
	}

	@Override
	public void doRender(RunTemplate runTemplate, Object data, XWPFTemplate template) throws Exception {
		if (null == data){
			return;
		}
		NiceXWPFDocument doc = template.getXWPFDocument();

		/*for (int i = 0; i < 2; i++) {
			XWPFRun run = runTemplate.getRun();
			// 定义行列
			int row = 6, col = 4;
			// 插入表格
			XWPFTable table = doc.insertNewTable(run, row, col);

			// 定义表格宽度、边框和样式
			TableTools.widthTable(table, MiniTableRenderData.WIDTH_A4_FULL, col);
			TableTools.borderTable(table, 4);

			// 调用XWPFTable API操作表格：data对象可以包含任意你想要的数据，包括图片文本等
			// 调用MiniTableRenderPolicy.Helper.renderRow方法快速方便的渲染一行数据
			// 调用TableTools类方法操作表格，比如合并单元格
			TableTools.mergeCellsHorizonal(table, 0, 1, 3);
			TableTools.mergeCellsHorizonal(table, 3, 1, 3);
			TableTools.mergeCellsHorizonal(table, 4, 1, 3);
			TableTools.mergeCellsHorizonal(table, 5, 1, 3);
			RowRenderData rowData = RowRenderData.build("高后果区编号");
			MiniTableRenderPolicy.Helper.renderRow(table, 0, rowData);
			RowRenderData rowData01 = RowRenderData.build("起始位置","","终止位置");
			MiniTableRenderPolicy.Helper.renderRow(table, 1, rowData01);
			RowRenderData rowData02 = RowRenderData.build("高后果区长度","","高后果区等级");
			MiniTableRenderPolicy.Helper.renderRow(table, 2, rowData02);
			RowRenderData rowData03 = RowRenderData.build("识别依据","《GB32167-2015油气输送管道完整性管理规范》");
			MiniTableRenderPolicy.Helper.renderRow(table, 3, rowData03);
			RowRenderData rowData04 = RowRenderData.build("管段描述");
			MiniTableRenderPolicy.Helper.renderRow(table, 4, rowData04);
			RowRenderData rowData05 = RowRenderData.build("高后果区地图");
			MiniTableRenderPolicy.Helper.renderRow(table, 5, rowData05);

		}*/
		/**管线数据***/
		XWPFRun run = runTemplate.getRun();
		// 定义行列
		int row = 6, col = 4;
		// 插入表格
		XWPFTable table = doc.insertNewTable(run, row, col);

		// 定义表格宽度、边框和样式
		TableTools.widthTable(table, MiniTableRenderData.WIDTH_A4_FULL, col);
		TableTools.borderTable(table, 4);

		// 调用XWPFTable API操作表格：data对象可以包含任意你想要的数据，包括图片文本等
		// 调用MiniTableRenderPolicy.Helper.renderRow方法快速方便的渲染一行数据
		// 调用TableTools类方法操作表格，比如合并单元格
		TableTools.mergeCellsHorizonal(table, 0, 1, 3);
		TableTools.mergeCellsHorizonal(table, 3, 1, 3);
		TableTools.mergeCellsHorizonal(table, 4, 1, 3);
		TableTools.mergeCellsHorizonal(table, 5, 1, 3);
		RowRenderData rowData = RowRenderData.build("高后果区编号");
		MiniTableRenderPolicy.Helper.renderRow(table, 0, rowData);
		RowRenderData rowData01 = RowRenderData.build("起始位置","","终止位置");
		MiniTableRenderPolicy.Helper.renderRow(table, 1, rowData01);
		RowRenderData rowData02 = RowRenderData.build("高后果区长度","","高后果区等级");
		MiniTableRenderPolicy.Helper.renderRow(table, 2, rowData02);
		RowRenderData rowData03 = RowRenderData.build("识别依据","《GB32167-2015油气输送管道完整性管理规范》");
		MiniTableRenderPolicy.Helper.renderRow(table, 3, rowData03);
		RowRenderData rowData04 = RowRenderData.build("管段描述");
		MiniTableRenderPolicy.Helper.renderRow(table, 4, rowData04);
		RowRenderData rowData05 = RowRenderData.build("高后果区地图");
		MiniTableRenderPolicy.Helper.renderRow(table, 5, rowData05);
	}

}
