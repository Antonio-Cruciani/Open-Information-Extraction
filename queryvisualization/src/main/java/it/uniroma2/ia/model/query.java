package it.uniroma2.ia.model;

public class query {
	private String sbj;
	private String pred;
	private String obj;
	public String getSbj() {
		return sbj;
	}
	public void setSbj(String sbj) {
		this.sbj = sbj;
	}
	public String getPred() {
		return pred;
	}
	public void setPred(String pred) {
		this.pred = pred;
	}
	public String getObj() {
		return obj;
	}
	public void setObj(String obj) {
		this.obj = obj;
	}
	@Override
	public String toString() {
		return "query [sbj=" + sbj + ", pred=" + pred + ", obj=" + obj + "]";
	}
}
