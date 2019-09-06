package cn.jasgroup.hcas.controller;

/**
 * @author kongchao
 * @version V1.0
 * @description TODO
 * @date 2019/8/22
 * @since JDK 1.80
 */
public class UniqueCheckBO {
    private String tableName ;
    private String fieldName;
    private Object fieldValue ;
    private String oid;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public Object getFieldValue() {
        return fieldValue;
    }

    public void setFieldValue(Object fieldValue) {
        this.fieldValue = fieldValue;
    }

	public String getOid() {
		return oid;
	}

	public void setOid(String oid) {
		this.oid = oid;
	}
    
}
