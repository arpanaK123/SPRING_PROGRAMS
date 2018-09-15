package com.bridgeit.model;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Component
public class TradeContractModel {
	@NotEmpty(message = "contract id is required")
	private String contractId;
	@NotEmpty(message = "content descr. is required")
	private String contentDescription;
	@NotEmpty(message = "contract money. is required")
	private int contractMoney;
	@NotEmpty(message = "exporter id is required")
	private String exporterId;
	@NotEmpty(message = "importer id is required")
	private String importerId;
	@NotEmpty(message = "importer bank id is required")
	private String importerBankId;
	@NotEmpty(message = "insurance id is required")
	private String insuranceId;
	@NotEmpty(message = "custom id is required")
	private String customId;
	@NotEmpty(message = "port of loading is required")
	private String portOfLoadin;
	@NotEmpty(message = "port of entry is required")
	private String portOfEntry;
	
	private boolean importerCheck;
	private boolean exporterCheck;
	private boolean customCheck;
	private boolean importerBankCheck;
	private boolean insuranceCheck;
	private boolean completeContract;

	
	public String getContractId() {
		return contractId;
	}
	public void setContractId(String contractId) {
		this.contractId = contractId;
	}
	public String getContentDescription() {
		return contentDescription;
	}
	public void setContentDescription(String contentDescription) {
		this.contentDescription = contentDescription;
	}
	public int getContractMoney() {
		return contractMoney;
	}
	public void setContractMoney(int contractMoney) {
		this.contractMoney = contractMoney;
	}
	public String getExporterId() {
		return exporterId;
	}
	public void setExporterId(String exporterId) {
		this.exporterId = exporterId;
	}
	public String getImporterId() {
		return importerId;
	}
	public void setImporterId(String importerId) {
		this.importerId = importerId;
	}
	public String getImporterBankId() {
		return importerBankId;
	}
	public void setImporterBankId(String importerBankId) {
		this.importerBankId = importerBankId;
	}
	public String getInsuranceId() {
		return insuranceId;
	}
	public void setInsuranceId(String insuranceId) {
		this.insuranceId = insuranceId;
	}
	public String getCustomId() {
		return customId;
	}
	public void setCustomId(String customId) {
		this.customId = customId;
	}
	public String getPortOfLoadin() {
		return portOfLoadin;
	}
	public void setPortOfLoadin(String portOfLoadin) {
		this.portOfLoadin = portOfLoadin;
	}
	public String getPortOfEntry() {
		return portOfEntry;
	}
	public void setPortOfEntry(String portOfEntry) {
		this.portOfEntry = portOfEntry;
	}
	public boolean isImporterCheck() {
		return importerCheck;
	}
	public void setImporterCheck(boolean importerCheck) {
		this.importerCheck = importerCheck;
	}
	public boolean isExporterCheck() {
		return exporterCheck;
	}
	public void setExporterCheck(boolean exporterCheck) {
		this.exporterCheck = exporterCheck;
	}
	public boolean isCustomCheck() {
		return customCheck;
	}
	public void setCustomCheck(boolean customCheck) {
		this.customCheck = customCheck;
	}
	public boolean isImporterBankCheck() {
		return importerBankCheck;
	}
	public void setImporterBankCheck(boolean importerBankCheck) {
		this.importerBankCheck = importerBankCheck;
	}
	public boolean isInsuranceCheck() {
		return insuranceCheck;
	}
	public void setInsuranceCheck(boolean insuranceCheck) {
		this.insuranceCheck = insuranceCheck;
	}
	public boolean isCompleteContract() {
		return completeContract;
	}
	public void setCompleteContract(boolean completeContract) {
		this.completeContract = completeContract;
	}
	

	
}
