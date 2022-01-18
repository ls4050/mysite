package com.poscoict.mysite.mvc.board;

import com.poscoict.web.mvc.Action;
import com.poscoict.web.mvc.ActionFactory;

public class BoardActionFactory extends ActionFactory {

	@Override
	public Action getAction(String actionName) {
		Action action = null;
		
		if ("delete".equals(actionName)) {
			action = new DeleteAction();
		} else if ("writeform".equals(actionName)) {
			action = new WriteFormAction();
		} else if ("write".equals(actionName)) {
			action = new WriteAction();
		} else if ("viewform".equals(actionName)) {
			action = new ViewFormAction();
		} else {
			action = new ListAction();
		}

		return action;
	}

}
