package cn.jasgroup.jasframework.acquisitiondata;

import java.util.List;

import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;

import com.deepoove.poi.data.RowRenderData;
import com.deepoove.poi.policy.DynamicTableRenderPolicy;
import com.deepoove.poi.policy.MiniTableRenderPolicy;
import com.deepoove.poi.util.TableTools;

/**
 * @description TODO
 * @author zhangyi
 * @date 2019年8月13日上午10:59:37
 * @version V1.0
 * @since JDK 1.80
 */

public class DetailTablePolicy extends DynamicTableRenderPolicy{

	// 货品填充数据所在行数
    int goodsStartRow = 2;
    // 人工费填充数据所在行数
    int laborsStartRow = 5;

    @Override
    public void render(XWPFTable table, Object data) {
        if (null == data) return;
        DetailData detailData = (DetailData) data;

        List<RowRenderData> labors = detailData.getLabors();
        if (null != labors) {
            table.removeRow(laborsStartRow);
            // 循环插入行
            for (int i = 0; i < labors.size(); i++) {
                XWPFTableRow insertNewTableRow = table.insertNewTableRow(laborsStartRow);
                for (int j = 0; j < 7; j++) insertNewTableRow.createCell();

                // 合并单元格
                TableTools.mergeCellsHorizonal(table, laborsStartRow, 0, 3);
                MiniTableRenderPolicy.Helper.renderRow(table, laborsStartRow, labors.get(i));
            }
        }

        List<RowRenderData> goods = detailData.getGoods();
        if (null != goods) {
            table.removeRow(goodsStartRow);
            for (int i = 0; i < goods.size(); i++) {
                XWPFTableRow insertNewTableRow = table.insertNewTableRow(goodsStartRow);
                for (int j = 0; j < 7; j++) insertNewTableRow.createCell();
                MiniTableRenderPolicy.Helper.renderRow(table, goodsStartRow, goods.get(i));
            }
        }
    }

}
