package uk.gov.digital.ho.proving.income.acl;

import uk.gov.digital.ho.proving.income.domain.Application;
import uk.gov.digital.ho.proving.income.domain.TemporaryMigrationFamilyApplication;

import java.util.Date;

public class JSONObjectBackedEarningsService implements EarningsService {
    @Override
    public Application lookup(String nino, Date applicationDate) {
        return new TemporaryMigrationFamilyApplication();
    }
}
