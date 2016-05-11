package com.blackducksoftware.integration.atlassian.mocks;

import com.atlassian.sal.api.transaction.TransactionCallback;
import com.atlassian.sal.api.transaction.TransactionTemplate;

public class TransactionTemplateMock implements TransactionTemplate {

	@Override
	public Object execute(final TransactionCallback action) {
		return action.doInTransaction();
	}

}
