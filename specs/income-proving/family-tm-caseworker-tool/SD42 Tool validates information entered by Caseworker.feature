Feature: Tool identifies Applicant meets Category A Financial Requirement

		National Insurance Numbers (NINO) - Format and Security: A NINO is made up of two letters, six numbers and a final letter (which is always A, B, C, or D)
		Date formats: Format should be dd/mm/yyyy or d/m/yyyy

  Scenario: Caseworker does NOT enter a National Insurance Number
    Given Caseworker is using the Income Proving Service Case Worker Tool
    When the NINO is NOT entered:
      | NINO      |  |
      | Application Received Date | 01/01/2015 |
    Then The service displays the following message:
      | Error Message | Please provide a National Insurance Number |
      | Error Field   | nino-error                                 |

  Scenario: Caseworker enters incorrect National Insurance Number prefixed with two characters
    Given Caseworker is using the Income Proving Service Case Worker Tool
    When an incorrect NINO is entered:
      | NINO      | 11123456A  |
      | Application Received Date | 01/01/2015 |
    Then The service displays the following message:
      | Error Message | Please provide a valid National Insurance Number |
      | Error Field   | nino-error                                 |

  Scenario: Caseworker enters incorrect National Insurance Number with two characters in the middle
    Given Caseworker is using the Income Proving Service Case Worker Tool
    When an incorrect NINO is entered:
      | NINO      | QQ12HR56A  |
      | Application Received Date | 01/01/2015 |
    Then The service displays the following message:
      | Error Message | Please provide a valid National Insurance Number |
      | Error Field   | nino-error                                 |

  Scenario: Caseworker enters incorrect National Insurance Number with the last digit being a number
    Given Caseworker is using the Income Proving Service Case Worker Tool
    When an incorrect NINO is entered:
      | NINO      | QQ1235560  |
      | Application Received Date | 01/01/2015 |
    Then The service displays the following message:
      | Error Message | Please provide a valid National Insurance Number |
      | Error Field   | nino-error                                 |

  Scenario: Caseworker enters incorrect National Insurance Number is not 9 characters
    Given Caseworker is using the Income Proving Service Case Worker Tool
    When an incorrect NINO is entered:
      | NINO      | QQ12545  |
      | Application Received Date | 01/01/2015 |
    Then The service displays the following message:
      | Error Message | Please provide a valid National Insurance Number |
      | Error Field   | nino-error                                 |

  Scenario: Caseworker enters an incorrect Application Received Date
    Given Caseworker is using the Income Proving Service Case Worker Tool
    When Application Received Date is not entered:
      | NINO      | QQ129956A  |
      | Application Received Date | 85/01/2015 |
    Then The service displays the following message:
      | Error Message | Please provide a valid Application Received Date |
      | Error Field   | application—received-date-error                  |

  Scenario: Caseworker enters an incorrect Application Received Date
    Given Caseworker is using the Income Proving Service Case Worker Tool
    When Application Received Date is not entered:
      | NINO      | QQ128856A  |
      | Application Received Date | 01/13/2015 |
    Then The service displays the following message:
      | Error Message | Please provide a valid Application Received Date |
      | Error Field   | application—received-date-error                  |

  Scenario: Caseworker enters an incorrect Application received date
    Given Caseworker is using the Income Proving Service Case Worker Tool
    When an incorrect Application Received Date is entered:
      | NINO      | QQ128856A  |
      | Application Received Date | 01/01/201k |
    Then The service displays the following message:
      | Error Message | Please provide a valid Application Received Date |
      | Error Field   | application—received-date-error                  |