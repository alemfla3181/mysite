package com.douzone.mysite.web.mvc.user;

import com.douzone.mysite.web.mvc.main.DefaultAction;
import com.douzone.web.mvc.Action;
import com.douzone.web.mvc.ActionFactory;

public class UserActionFactory extends ActionFactory {

	@Override
	public Action getAction(String actionName) {
		Action action = null;

		System.out.println(actionName);
		if ("joinform".equals(actionName)) {
			action = new JoinFormAction();
		} else if ("joinsuccess".equals(actionName)) {
			action = new JoinSuccess();

		} else if ("join".equals(actionName)) {
			action = new JoinAction();
		} else {
			action = new DefaultAction();
		}

		return action;
	}

}