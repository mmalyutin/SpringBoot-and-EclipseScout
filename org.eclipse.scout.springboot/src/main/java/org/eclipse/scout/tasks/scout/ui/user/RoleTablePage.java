package org.eclipse.scout.tasks.scout.ui.user;

import java.util.Collection;
import java.util.Set;
import java.util.UUID;

import javax.inject.Inject;

import org.eclipse.scout.rt.client.ui.action.menu.AbstractMenu;
import org.eclipse.scout.rt.client.ui.action.menu.IMenuType;
import org.eclipse.scout.rt.client.ui.action.menu.TableMenuType;
import org.eclipse.scout.rt.client.ui.basic.table.AbstractTable;
import org.eclipse.scout.rt.client.ui.basic.table.ITableRow;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractStringColumn;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.AbstractPageWithTable;
import org.eclipse.scout.rt.client.ui.form.FormEvent;
import org.eclipse.scout.rt.client.ui.form.FormListener;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Bean;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.util.CollectionUtility;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;
import org.eclipse.scout.tasks.model.Role;
import org.eclipse.scout.tasks.scout.ui.user.RoleTablePage.Table;
import org.eclipse.scout.tasks.spring.service.RoleService;

@Bean
public class RoleTablePage extends AbstractPageWithTable<Table> {

  @Inject
  private RoleService roleService;

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("RoleTablePage");
  }

  @Override
  protected boolean getConfiguredLeaf() {
    return true;
  }

  @Override
  protected void execInitPage() {
    setVisiblePermission(new ReadRolePermission());
  }

  @Override
  protected void execLoadData(SearchFilter filter) {
    Collection<Role> roles = roleService.getRoles();
    importTableRowData(roles);
  }

  private void importTableRowData(Collection<Role> roles) {
    Table table = getTable();

    table.deleteAllRows();

    if (roles == null || roles.size() == 0) {
      return;
    }

    for (Role role : roles) {
      ITableRow row = table.createRow();
      table.getIdColumn().setValue(row, role.getId());
      table.getNameColumn().setValue(row, role.getName());
      table.addRow(row);
    }
  }

  public class Table extends AbstractTable {

    @Override
    protected void execRowAction(ITableRow row) {
      getMenuByClass(EditMenu.class).execAction();
    }

    public NameColumn getNameColumn() {
      return getColumnSet().getColumnByClass(NameColumn.class);
    }

    public IdColumn getIdColumn() {
      return getColumnSet().getColumnByClass(IdColumn.class);
    }

    @Order(1000)
    public class NewMenu extends AbstractMenu {

      @Override
      protected String getConfiguredText() {
        return TEXTS.get("New");
      }

      @Override
      protected String getConfiguredIconId() {
        // get unicode from http://fontawesome.io/icon/magic/
        return "font:awesomeIcons \uf0d0";

      }

      @Override
      protected Set<? extends IMenuType> getConfiguredMenuTypes() {
        return CollectionUtility.hashSet(TableMenuType.EmptySpace, TableMenuType.SingleSelection, TableMenuType.MultiSelection);
      }

      @Override
      protected void execAction() {
        RoleForm form = BEANS.get(RoleForm.class);
        form.addFormListener(new RoleFormListener());
        form.startNew();
      }
    }

    @Order(2000)
    public class EditMenu extends AbstractMenu {

      @Override
      protected String getConfiguredText() {
        return TEXTS.get("Edit");
      }

      @Override
      protected String getConfiguredIconId() {
        // get unicode from http://fontawesome.io/icon/pencil/
        return "font:awesomeIcons \uf040";
      }

      @Override
      protected Set<? extends IMenuType> getConfiguredMenuTypes() {
        return CollectionUtility.hashSet(TableMenuType.SingleSelection);
      }

      @Override
      protected void execAction() {
        String name = getNameColumn().getSelectedValue();

        RoleForm form = BEANS.get(RoleForm.class);
        form.addFormListener(new RoleFormListener());
        form.getRoleNameField().setValue(name);
        form.getRoleNameField().setEnabled(false);
        form.startModify();
      }
    }

    private class RoleFormListener implements FormListener {

      @Override
      public void formChanged(FormEvent e) {
        // reload page to reflect new/changed data after saving any changes
        if (FormEvent.TYPE_CLOSED == e.getType() && e.getForm().isFormStored()) {
          reloadPage();
        }
      }
    }

    @Order(1000)
    public class IdColumn extends AbstractColumn<UUID> {

      @Override
      protected boolean getConfiguredDisplayable() {
        return false;
      }

      @Override
      protected boolean getConfiguredPrimaryKey() {
        return true;
      }
    }

    @Order(2000)
    public class NameColumn extends AbstractStringColumn {
      @Override
      protected String getConfiguredHeaderText() {
        return TEXTS.get("RoleName");
      }

      @Override
      protected int getConfiguredWidth() {
        return 150;
      }
    }
  }
}
