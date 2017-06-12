package fr.insalyon.creatis.vip.cli.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "InfoExecution")
public class InfoExecution implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int Id;
	@Column(name = "ExecutionIdentifier")
	private String executionIdentifier;
	@Column(name="PipelineIdentifier")
	private String pipelineIdentifier;
	@Column(name = "Status")
	private String status;
	@Column(name = "Repersitory")
	private String repersitory;
	@Column(name = "StartDate")
	private Long startdate;
	
	
	public InfoExecution() {

	}

	public InfoExecution(String executionIdentifier,String pipelineIdentifier, String status, String repersitory, Long startdate) {
		super();
		this.executionIdentifier = executionIdentifier;
		this.pipelineIdentifier=pipelineIdentifier;
		this.status = status;
		this.repersitory = repersitory;
		this.startdate = startdate;
	}

	

	@Override
	public String toString() {
		return "***InfoExecution***\r\n executionIdentifier=" + executionIdentifier + "\r\n pipelineIdentifier="
				+ pipelineIdentifier + "\r\n status=" + status + "\r\n repersitory=" + repersitory + "\r\n startdate="
				+ startdate + "";
	}

	public String getPipelineIdentifier() {
		return pipelineIdentifier;
	}

	public void setPipelineIdentifier(String pipelineIdentifier) {
		this.pipelineIdentifier = pipelineIdentifier;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((executionIdentifier == null) ? 0 : executionIdentifier.hashCode());
		return result;
	}

	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		InfoExecution other = (InfoExecution) obj;
		if (executionIdentifier == null) {
			if (other.executionIdentifier != null)
				return false;
		} else if (!executionIdentifier.equals(other.executionIdentifier))
			return false;
		return true;
	}

	public String getExecutionIdentifier() {
		return executionIdentifier;
	}

	public void setExecutionIdentifier(String executionIdentifier) {
		this.executionIdentifier = executionIdentifier;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRepersitory() {
		return repersitory;
	}

	public void setRepersitory(String repersitory) {
		this.repersitory = repersitory;
	}

	public Long getStartdate() {
		return startdate;
	}

	public void setStartdate(Long startdate) {
		this.startdate = startdate;
	}

}
