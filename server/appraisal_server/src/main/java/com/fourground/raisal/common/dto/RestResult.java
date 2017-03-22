package com.fourground.raisal.common.dto;

public class RestResult extends BaseModel {

	private static final long serialVersionUID = 7054985785308631595L;
	
	private boolean success = true;
	private String error;
	private int targetRow;
	private String targetId;
	private long recordTotal;
	private long recordFiltered;
	private long rowCnt;
	
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	public int getTargetRow() {
		return targetRow;
	}
	public void setTargetRow(int targetRow) {
		this.targetRow = targetRow;
	}
	public String getTargetId() {
		return targetId;
	}
	public void setTargetId(String targetId) {
		this.targetId = targetId;
	}
	public long getRecordTotal() {
		return recordTotal;
	}
	public void setRecordTotal(long recordTotal) {
		this.recordTotal = recordTotal;
	}
	public long getRecordFiltered() {
		return recordFiltered;
	}
	public void setRecordFiltered(long recordFiltered) {
		this.recordFiltered = recordFiltered;
	}
	public long getRowCnt() {
		return rowCnt;
	}
	public void setRowCnt(long rowCnt) {
		this.rowCnt = rowCnt;
	}
}
