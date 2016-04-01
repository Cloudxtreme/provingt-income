package uk.gov.digital.ho.proving.income.acl;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.gov.digital.ho.proving.income.domain.Application;
import uk.gov.digital.ho.proving.income.domain.TemporaryMigrationFamilyApplication;

import java.io.File;
import java.io.IOException;
import java.util.Date;

public class JSONObjectBackedEarningsService implements EarningsService {
    private Logger LOGGER = LoggerFactory.getLogger(JSONObjectBackedEarningsService.class);

    @Override
    public Application lookup(String nino, Date applicationDate) {

        ObjectMapper mapper = new ObjectMapper();

        TemporaryMigrationFamilyApplication application = null;

        try {
            application = mapper.readValue(new File("src/main/resources/json/" + nino + ".json"), TemporaryMigrationFamilyApplication.class);
            application.getFinancialRequirementsCheck().setApplicationReceivedDate(applicationDate);
            LOGGER.debug("Read JSON: " + application);
        } catch (IOException e) {
            e.printStackTrace();
        }

//        return new TemporaryMigrationFamilyApplication();
        return application;
    }
}
