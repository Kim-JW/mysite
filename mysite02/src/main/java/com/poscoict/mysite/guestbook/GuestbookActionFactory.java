package com.poscoict.mysite.guestbook;

import com.poscoict.web.mvc.Action;
import com.poscoict.web.mvc.ActionFactory;

public class GuestbookActionFactory extends ActionFactory {

	@Override
	public Action getAction(String actionName) {
		Action action = null;
		
		if("deleteform".equals(actionName)) {
			action = new DeleteForm();
		} else if("delete".equals(actionName)) {
			action = new Delete();
		} else if("add".equals(actionName)) {
			action = new Add();
		}else {
			action = new IndexAction();
		}
		
		return action;
	}

}
