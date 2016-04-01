Feature: Tool identifies Applicant does not meet the Category A Financial Requirement

		Requirement to meet Category A
			Applicant or Sponsor has been paid for 6 consecutive months with the same employer

		Financial employment income regulation to pass this Feature File
			Applicant or Sponsor has earned < £1550 Gross Income in any one of the 6 months prior to the Application Received Date

  Scenario: Jill does not meet the Category A Financial Requirement (She has earned < the Cat A financial threshold)

		Pay date 15th of the month
		Before day of Application Received Date
		She earns £1000 Monthly Gross Income EVERY of the 6 months prior to the Application Received Date

   Given Caseworker is using the Income Proving Service Case Worker Tool
    When Robert submits a query:
      | NINO      | JL123456A  |
      | Application Received Date | 15/01/2015 |
    Then The service provides the following result:

		| Your Search Individual Name | Jill Lewondoski |
		| Your Search National Insurance Number | JL123456A | 
		| Your Search Application Received Date | 23/01/2015 |


  Scenario: Francois does not meet the Category A Financial Requirement (He has earned < the Cat A financial threshold)

		Pay date 28th of the month 
		After day of application received date
		He earns £1250 Monthly Gross Income EVERY of the 6 months prior to the Application Received Date

   Given Caseworker is using the Income Proving Service Case Worker Tool
    When Robert submits a query:
      | NINO      | BB123456B  |
      | Application received date | 28/03/2015 |
    Then The service provides the following result:

		| Your Search Individual Name | Francois Leblanc |
		| Your Search National Insurance Number | BB123456B | 
		| Your Search Application Received Date | 23/01/2015 |

  Scenario: Carlos does not meet the Category A employment duration Requirement (He has worked for his current employer for only 3 months)

		Pay date 3rd of the month
		On same day of application received date
		He earns £1307 Monthly Gross Income BUT for only 3 months prior to the Application Received Date
			He worked for a different employer before his current employer

   Given Caseworker is using the Income Proving Service Case Worker Tool
    When Robert submits a query:
      | NINO      | CC123456C  |
      | Application received date | 03/07/2015 |
    Then The service provides the following result:

		| Your Search Individual Name | Carlos Santiago |
		| Your Search National Insurance Number | CC123456C | 
		| Your Search Application Received Date | 23/01/2015 |

