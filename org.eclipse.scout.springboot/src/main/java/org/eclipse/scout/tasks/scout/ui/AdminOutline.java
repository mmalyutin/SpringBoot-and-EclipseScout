package org.eclipse.scout.tasks.scout.ui;

import java.util.List;

import org.eclipse.scout.rt.client.ui.desktop.outline.AbstractOutline;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.IPage;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Bean;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.tasks.scout.ui.user.RoleTablePage;
import org.eclipse.scout.tasks.scout.ui.user.UserTablePage;

@Bean
public class AdminOutline extends AbstractOutline {

  // get unicode http://fontawesome.io/icon/users/
  public static final String OUTLINE_ICON = "font:awesomeIcons \uf0c0";

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("Administration");
  }

  @Override
  protected String getConfiguredIconId() {
    return OUTLINE_ICON;
  }

  @Override
  protected void execCreateChildPages(List<IPage<?>> pageList) {
    pageList.add(BEANS.get(UserTablePage.class));
    pageList.add(BEANS.get(RoleTablePage.class));
  }
}
