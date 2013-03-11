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
// BEGIN GENERATED CODE
package gov.redhawk.ide.codegen.jet.python.template.component.workmodule;

import gov.redhawk.ide.codegen.jet.TemplateParameter;
import gov.redhawk.ide.codegen.ImplementationSettings;

	/**
    * @generated
    */

public class WorkModuleTemplate
{

  protected static String nl;
  public static synchronized WorkModuleTemplate create(String lineSeparator)
  {
    nl = lineSeparator;
    WorkModuleTemplate result = new WorkModuleTemplate();
    nl = null;
    return result;
  }

  public final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "#!/usr/bin/env python" + NL + "import Queue" + NL + "import threading" + NL + "import os, time";
  protected final String TEXT_2 = NL + "from XMinter import *";
  protected final String TEXT_3 = NL + NL + "class WorkClass:" + NL + "    \"\"\"" + NL + "    This class provides a place for the main processing of the" + NL + "       component to reside." + NL + "    \"\"\"" + NL + "" + NL + "    def __init__(self, parent):" + NL + "        self.parent = parent" + NL + "        " + NL + "        # Initialize variables for input data processing" + NL + "        self.data_queue = Queue.Queue()" + NL + "        self.empty_queue = False" + NL + "        " + NL + "        # variables for thread management" + NL + "        self.is_running = True" + NL + "        self.timeout_check_period = 0.1 # this is in seconds" + NL + "        self.process_thread_released = False" + NL + "        " + NL + "        # create mutex locks for handling issues with Reset" + NL + "        self.reset_lock = threading.Lock()" + NL + "        self.reset_signal = threading.Event()" + NL + "        " + NL + "        # create and start the main thread" + NL + "        self.process_thread = threading.Thread(target=self.Process)" + NL + "        self.process_thread.start()" + NL + "        " + NL + "    def __del__(self):" + NL + "        \"\"\"" + NL + "        Destructor" + NL + "        \"\"\"" + NL + "        self.Release()" + NL + "        " + NL + "    def Release(self):" + NL + "        self.reset_signal.clear()" + NL + "        self.reset_lock.acquire()" + NL + "" + NL + "        self.is_running = False" + NL + "        " + NL + "        self.reset_signal.set()" + NL + "        self.reset_lock.release()" + NL + "        " + NL + "        while not self.process_thread_released:" + NL + "            time.sleep(0.01)" + NL + "            " + NL + "            " + NL + "    def Reset(self, external_flag=True):" + NL + "        \"\"\"" + NL + "        Reset the module to a known state." + NL + "        \"\"\"" + NL + "        " + NL + "        if external_flag:" + NL + "            self.reset_signal.clear()" + NL + "            self.reset_lock.acquire()" + NL + "            " + NL + "            # Empty out the data queue if desired" + NL + "            if self.empty_queue:" + NL + "                try:" + NL + "                    while self.data_queue.get_nowait():" + NL + "                        pass" + NL + "                except Queue.Empty:" + NL + "                    pass" + NL + "            " + NL + "        #" + NL + "        # Clear out any data variables" + NL + "        # TODO:" + NL + "        " + NL + "        " + NL + "        if external_flag:    " + NL + "            self.reset_signal.set()" + NL + "            self.reset_lock.release()" + NL + "" + NL + "    def queueData(self, data, T, EOS, streamID):" + NL + "        \"\"\"" + NL + "        Add data to main processing queue." + NL + "        \"\"\"" + NL + "        tmpmsg = (data, T, EOS, streamID)" + NL + "        self.data_queue.put(tmpmsg)" + NL + "        " + NL + "    # Main processing thread" + NL + "    def Process(self):" + NL + "        new_data_flag = False" + NL + "        new_data = None" + NL + "" + NL + "        # set signal initially to enter main loop" + NL + "        self.reset_signal.set()" + NL + "        " + NL + "        # main processing loop" + NL + "        while self.is_running:" + NL + "            # make's sure it is OK to process data from the queue" + NL + "            self.reset_signal.wait()" + NL + "            self.reset_lock.acquire()" + NL + "            " + NL + "            # check to see if component has been released" + NL + "            if not self.is_running:" + NL + "                self.reset_lock.release()" + NL + "                continue" + NL + "" + NL + "            # get new message from queue if available" + NL + "            try:" + NL + "                new_data = self.data_queue.get(timeout=self.timeout_check_period)" + NL + "                new_data_flag = True" + NL + "            except Queue.Empty:" + NL + "                new_data_flag = False" + NL + "" + NL + "            if new_data_flag:" + NL + "                # extract info from new data" + NL + "                data, T, EOS, streamID = new_data" + NL + "" + NL + "                #" + NL + "                # deal with new data here" + NL + "                # TODO:" + NL + "                " + NL + "            # ensures that nothing is reset while processing" + NL + "            self.reset_lock.release()" + NL + "            " + NL + "        # Let Release function know it's now OK to close" + NL + "        self.process_thread_released = True";
  protected final String TEXT_4 = NL;

    /**
    * {@inheritDoc}
    */

    public String generate(Object argument)
  {
    final StringBuffer stringBuffer = new StringBuffer();
     
    TemplateParameter templ = (TemplateParameter) argument;
    ImplementationSettings implSettings = templ.getImplSettings();

    stringBuffer.append(TEXT_1);
    
	if (implSettings.getGeneratorId().contains("XMPY")) {

    stringBuffer.append(TEXT_2);
    
	}

    stringBuffer.append(TEXT_3);
    stringBuffer.append(TEXT_4);
    return stringBuffer.toString();
  }
} 