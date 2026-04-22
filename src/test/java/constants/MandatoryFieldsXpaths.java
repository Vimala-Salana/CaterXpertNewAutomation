package constants;

public class MandatoryFieldsXpaths {

	 public static final String BUSINESS_UNIT =  "(//span[text()='- Select Business Unit -'] | //div[contains(text(),'Select Business Unit')])";
	 public static final String MANDATORY_LABEL = "//label[.//span[text()='*']]";
	    public static final String TEXT_INPUT = "../following-sibling::div[1]//input[not(@readonly) and (@type='search')] | ../following-sibling::div[1]//textarea";
	    public static final String DROPDOWN = "following-sibling::div[1]//div[@class='p-multiselect-trigger'] | ../following-sibling::div[1]//div[@class='p-multiselect-trigger'] | following::div[1]//span[contains(@class,'p-dropdown-trigger-icon')]";
	    public static final String DROPDOWN_PLACEHOLDER = "following::div[1]//span[contains(@class,'p-dropdown-label')] | ../following-sibling::div[1]//input[contains(@class, 'p-dropdown-label')]";
	    public static final String MULTISELECT_LIST = "//ul[contains(@class,'p-multiselect-items')]//li";
	    public static final String DROPDOWN_LIST = "//ul[contains(@class,'p-dropdown-items')]//li";
	    public static final String DATE_INPUT = "../following-sibling::div[1]//input[@readonly and @placeholder='MM/DD/YYYY']";
	    public static final String TIME_FIELD = "../following-sibling::div//span[normalize-space(text())='schedule']";
	    public static final String TIME_OPTIONS = "//span[contains(@class,'AvenirLTStd-Medium')]";
	    public static final String OVERLAY = "div.overlay";
	    
}
