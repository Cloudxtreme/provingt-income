Feature: Tool identifies Applicant meets Category A Financial Requirement

	Requirement to meet Category A
	Applicant or Sponsor has been paid for 6 consecutive months with the same employer

	Financial employment income regulation to pass this Feature File
	Applicant or Sponsor has earned => £1550 Monthly Gross Income EVERY of the 6 months prior to the Application Received Date

	Scenario: Jon meets the Category A Financial Requirement

	Pay date 15th of the month
	Before day of application received date
	He earns £1600 Monthly Gross Income EVERY of the 6 months prior to the Application Received Date

		When Client submits a query to IPS Family TM Case Worker Tool:
			| NINO      | AA123456A  |
			| Application received date | 23-01-2015 |
		Then The IPS API provides the following result:
			| Outcome                               | Success    |
			| Outcome Box Individual Name           | Jon Taylor |
			| Outcome Employment Threshold          | £18,600    |
			| Outcome From Date                     | 23-07-2014 |
			| Outcome To Date                       | 23-01-2015 |
			| Your Search Individual Name           | Jon Taylor |
			| Your Search National Insurance Number | AA123456A  |
			| Your Search Application Received Date | 23-01-2015 |
