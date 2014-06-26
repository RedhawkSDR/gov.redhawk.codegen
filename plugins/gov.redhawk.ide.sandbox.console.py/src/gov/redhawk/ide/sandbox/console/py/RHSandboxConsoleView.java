/*******************************************************************************
 * This file is protected by Copyright. 
 * Please refer to the COPYRIGHT file distributed with this source distribution.
 *
 * This file is part of REDHAWK IDE.
 *
 * All rights reserved.  This program and the accompanying materials are made available under 
 * the terms of the Eclipse Public License v1.0 which accompanies this distribution, and is available at 
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package gov.redhawk.ide.sandbox.console.py;

import gov.redhawk.ide.sandbox.console.py.SandboxConsole.ITerminateListener;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.action.GroupMarker;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.console.IConsole;
import org.eclipse.ui.console.IConsoleView;
import org.eclipse.ui.part.IPageBookViewPage;
import org.eclipse.ui.part.IPageSite;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.ui.statushandlers.StatusManager;

public class RHSandboxConsoleView extends ViewPart implements IConsoleView, IPageSite, ITerminateListener {

	private SandboxConsole console;
	private Composite consoleArea;
	private IPageBookViewPage page;

	public RHSandboxConsoleView() {
	}

	@Override
	public void createPartControl(Composite parent) {
		consoleArea = new Composite(parent, SWT.NONE);
		consoleArea.setLayout(new FillLayout());
		try {
			createConsoleControl();
		} catch (final Exception e) { // SUPPRESS CHECKSTYLE Logged Error
			StatusManager.getManager().handle(new Status(IStatus.ERROR, RHLocalConsolePlugin.PLUGIN_ID, Messages.RHLocalConsoleFactory_PY_ERROR, e),
				StatusManager.LOG | StatusManager.SHOW);
		}
	}

	public void createConsoleControl() {
		if ((this.console != null) || (this.page != null)) {
			throw new IllegalStateException();
		}

		getViewSite().getActionBars().getToolBarManager().removeAll();
		getViewSite().getActionBars().getToolBarManager().add(new GroupMarker("launchGroup"));
		getViewSite().getActionBars().getToolBarManager().add(new GroupMarker("outputGroup"));

		try {
			console = SandboxConsole.create();
			page = console.createPage(this);
			page.init(this);
			page.createControl(consoleArea);
			consoleArea.update();
			consoleArea.redraw();
			this.console.addTerminateListener(this);
		} catch (CoreException e) {
			RHLocalConsolePlugin.getDefault().getLog().log(new Status(e.getStatus().getSeverity(), RHLocalConsolePlugin.PLUGIN_ID, "Failed to create Sandbox console" , e));
			this.console = null;
		}
	}

	@Override
	public void consoleTerminated(SandboxConsole console) {
		if (this.console != console) {
			throw new IllegalArgumentException("Unexpected console received");
		}
		this.console = null;

		getShell().getDisplay().asyncExec(new Runnable() {

			@Override
			public void run() {
				if ((RHSandboxConsoleView.this.page != null) && (RHSandboxConsoleView.this.page.getControl() != null)
					&& (!RHSandboxConsoleView.this.page.getControl().isDisposed())) {
					RHSandboxConsoleView.this.page.getControl().dispose();
					RHSandboxConsoleView.this.page.dispose();
				}
				RHSandboxConsoleView.this.page = null;

				RHSandboxConsoleView.this.createConsoleControl();
				RHSandboxConsoleView.this.consoleArea.layout();
			}
		});
	}

	@Override
	public void setFocus() {
		// TODO Auto-generated method stub

	}

	@Override
	public void display(final IConsole console) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setPinned(final boolean pin) {
		// TODO Auto-generated method stub

	}

	@Override
	public void pin(final IConsole console) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isPinned() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public IConsole getConsole() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void warnOfContentChange(final IConsole console) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setScrollLock(final boolean scrollLock) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean getScrollLock() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public IWorkbenchPage getPage() {
		return this.getSite().getPage();
	}

	@Override
	public ISelectionProvider getSelectionProvider() {
		return this.getSite().getSelectionProvider();
	}

	@Override
	public Shell getShell() {
		return this.getSite().getShell();
	}

	@Override
	public IWorkbenchWindow getWorkbenchWindow() {
		return this.getSite().getWorkbenchWindow();
	}

	@Override
	public void setSelectionProvider(final ISelectionProvider provider) {
		this.getSite().setSelectionProvider(provider);
	}

	@Override
	public Object getService(@SuppressWarnings("rawtypes") final Class api) {
		return this.getSite().getService(api);
	}

	@Override
	public boolean hasService(@SuppressWarnings("rawtypes") final Class api) {
		return this.getSite().hasService(api);
	}

	@Override
	public void registerContextMenu(final String menuId, final MenuManager menuManager, final ISelectionProvider selectionProvider) {
		this.getSite().registerContextMenu(menuId, menuManager, selectionProvider);
	}

	@Override
	public IActionBars getActionBars() {
		return getViewSite().getActionBars();
	}

}
