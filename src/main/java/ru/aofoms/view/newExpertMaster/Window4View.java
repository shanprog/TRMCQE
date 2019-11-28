package ru.aofoms.view.newExpertMaster;

import de.felixroske.jfxsupport.AbstractFxmlView;
import de.felixroske.jfxsupport.FXMLView;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;

@FXMLView("/fxml/newExpertMaster/window4.fxml")
@Scope(value = "prototype", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class Window4View extends AbstractFxmlView {
}
