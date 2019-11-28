package ru.aofoms.view;

import de.felixroske.jfxsupport.AbstractFxmlView;
import de.felixroske.jfxsupport.FXMLView;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;

@FXMLView("/fxml/userList.fxml")
@Scope(value = "prototype", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class UserListView extends AbstractFxmlView {

}
