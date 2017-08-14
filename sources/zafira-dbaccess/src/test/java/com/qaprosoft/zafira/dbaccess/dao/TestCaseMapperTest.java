package com.qaprosoft.zafira.dbaccess.dao;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;
import static org.testng.Assert.assertNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import com.qaprosoft.zafira.dbaccess.dao.mysql.TestCaseMapper;
import com.qaprosoft.zafira.models.db.Project;
import com.qaprosoft.zafira.models.db.Status;
import com.qaprosoft.zafira.models.db.TestCase;
import com.qaprosoft.zafira.models.db.User;

@Test
@ContextConfiguration("classpath:com/qaprosoft/zafira/dbaccess/dbaccess-test.xml")
public class TestCaseMapperTest extends AbstractTestNGSpringContextTests
{
	/**
	 * Turn this on to enable this test
	 */
	private static final boolean ENABLED = false;
	
	private static final TestCase TEST_CASE = new TestCase()
	{
		private static final long serialVersionUID = 1L;
		{
			User user1 = new User();
			user1.setId(1L);
			
			User user2 = new User();
			user2.setId(5L);
			
			Project project = new Project();
			project.setId(2L);
			
			setPrimaryOwner(user1);
			setSecondaryOwner(user2);
			setProject(project);
			setTestSuiteId(1L);
			setTestClass("com.qaprosoft.Test");
			setTestMethod("test");
			setInfo("Run me!");
			setStatus(Status.FAILED);
		}
	};

	@Autowired
	private TestCaseMapper testCaseMapper;

	@Test(enabled = ENABLED)
	public void createTestCase()
	{
		testCaseMapper.createTestCase(TEST_CASE);

		assertNotEquals(TEST_CASE.getId(), 0, "TestCase ID must be set up by autogenerated keys");
	}
	
	@Test(enabled = ENABLED, dependsOnMethods =
		{ "createTestCase" })
	public void test()
	{
		testCaseMapper.getTestCasesByUsername("akhursevich");
	}
	

	@Test(enabled = ENABLED, dependsOnMethods =
	{ "createTestCase" })
	public void getTestCaseById()
	{
		checkTestCase(testCaseMapper.getTestCaseById(TEST_CASE.getId()));
	}

	@Test(enabled = ENABLED, dependsOnMethods =
	{ "createTestCase" })
	public void getTestCaseByClassAndMethod()
	{
		checkTestCase(testCaseMapper.getTestCaseByClassAndMethod(TEST_CASE.getTestClass(), TEST_CASE.getTestMethod()));
	}

	@Test(enabled = ENABLED, dependsOnMethods =
	{ "createTestCase" })
	public void updateTestCase()
	{
		TEST_CASE.getPrimaryOwner().setId(5L);
		TEST_CASE.getSecondaryOwner().setId(1L);
		TEST_CASE.getProject().setId(2L);
		TEST_CASE.setTestSuiteId(1L);
		TEST_CASE.setTestClass("com.qaprosoft.SuperTest");
		TEST_CASE.setTestMethod("test2");
		TEST_CASE.setInfo("Do not run me!");
		TEST_CASE.setStatus(Status.PASSED);
		
		testCaseMapper.updateTestCase(TEST_CASE);

		checkTestCase(testCaseMapper.getTestCaseById(TEST_CASE.getId()));
	}

	/**
	 * Turn this in to delete testCase after all tests
	 */
	private static final boolean DELETE_ENABLED = true;

	/**
	 * If true, then <code>deleteTestCase</code> will be used to delete testCase after all tests, otherwise -
	 * <code>deleteTestCaseById</code>
	 */
	private static final boolean DELETE_BY_TEST_CASE = false;

	@Test(enabled = ENABLED && DELETE_ENABLED && DELETE_BY_TEST_CASE, dependsOnMethods =
	{ "createTestCase", "getTestCaseById", "getTestCaseByClassAndMethod", "updateTestCase" })
	public void deleteTestCase()
	{
		testCaseMapper.deleteTestCase(TEST_CASE);

		assertNull(testCaseMapper.getTestCaseById(TEST_CASE.getId()));
	}

	@Test(enabled = ENABLED && DELETE_ENABLED && !DELETE_BY_TEST_CASE, dependsOnMethods =
	{ "createTestCase", "getTestCaseById", "getTestCaseByClassAndMethod", "updateTestCase" })
	public void deleteTestCaseById()
	{
		testCaseMapper.deleteTestCaseById((TEST_CASE.getId()));

		assertNull(testCaseMapper.getTestCaseById(TEST_CASE.getId()));
	}

	private void checkTestCase(TestCase testCase)
	{
		assertEquals(testCase.getPrimaryOwner().getId(), TEST_CASE.getPrimaryOwner().getId(), "User ID must match");
		assertEquals(testCase.getTestSuiteId(), TEST_CASE.getTestSuiteId(), "Test suite ID must match");
		assertEquals(testCase.getTestClass(), TEST_CASE.getTestClass(), "Test case class must match");
		assertEquals(testCase.getTestMethod(), TEST_CASE.getTestMethod(), "Test case method must match");
		assertEquals(testCase.getInfo(), TEST_CASE.getInfo(), "Info must match");
		assertEquals(testCase.getStatus(), TEST_CASE.getStatus(), "Status must match");
		assertEquals(testCase.getProject().getId(), TEST_CASE.getProject().getId(), "Project must match");
	}
}
