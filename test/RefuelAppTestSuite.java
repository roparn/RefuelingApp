import models.FuelEntryTableModelTest;
import models.FuelEntryTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import swing.MainAppMenuTest;

import dao.FileDaoTest;

@RunWith(Suite.class)
@SuiteClasses({ FuelEntryTest.class, FileDaoTest.class, MainAppMenuTest.class, FuelEntryTableModelTest.class })
public class RefuelAppTestSuite {
}
