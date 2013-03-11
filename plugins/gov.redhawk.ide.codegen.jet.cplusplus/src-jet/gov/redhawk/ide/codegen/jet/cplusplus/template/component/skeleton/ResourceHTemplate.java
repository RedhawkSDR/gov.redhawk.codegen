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
package gov.redhawk.ide.codegen.jet.cplusplus.template.component.skeleton;

import gov.redhawk.ide.codegen.ImplementationSettings;
import mil.jpeojtrs.sca.spd.SoftPkg;
import mil.jpeojtrs.sca.spd.Implementation;
import gov.redhawk.ide.codegen.jet.TemplateParameter;

	/**
    * @generated
    */

public class ResourceHTemplate
{

  protected static String nl;
  public static synchronized ResourceHTemplate create(String lineSeparator)
  {
    nl = lineSeparator;
    ResourceHTemplate result = new ResourceHTemplate();
    nl = null;
    return result;
  }

  public final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "#ifndef ";
  protected final String TEXT_2 = "_IMPL_H" + NL + "#define ";
  protected final String TEXT_3 = "_IMPL_H" + NL + "" + NL + "#include <stdlib.h>" + NL + "#include <string>" + NL + "#include <map>" + NL + "#include <list>" + NL + "" + NL + "#include \"CF/cf.h\"" + NL + "" + NL + "#include \"ossie/Resource_impl.h\"" + NL + "#include \"ossie/ossieSupport.h\"" + NL + "using namespace std;" + NL + "#include <sys/time.h>" + NL + "#include <queue>" + NL + "#include <fstream>" + NL + "" + NL + "class ";
  protected final String TEXT_4 = "_i;" + NL + "class ";
  protected final String TEXT_5 = "_i : public Resource_impl, public omni_thread" + NL + "{" + NL + "" + NL + "    public:";
  protected final String TEXT_6 = NL + "        ";
  protected final String TEXT_7 = "_i(const char *uuid, omni_condition *con, const char *, ossie::ORB *);" + NL + "" + NL + "        ~";
  protected final String TEXT_8 = "_i(void);" + NL + "" + NL + "        char *identifier () throw (CORBA::SystemException);" + NL + "" + NL + "        // CF::Resource" + NL + "        void start() throw (CF::Resource::StartError, CORBA::SystemException);" + NL + "" + NL + "        void stop() throw (CF::Resource::StopError, CORBA::SystemException);" + NL + "" + NL + "        // CF::LifeCycle" + NL + "        void releaseObject() throw (CF::LifeCycle::ReleaseError, CORBA::SystemException);" + NL + "" + NL + "        void initialize() throw (CF::LifeCycle::InitializeError, CORBA::SystemException);" + NL + "" + NL + "        // CF::PortSupplier" + NL + "        CORBA::Object* getPort (const char *) throw (CF::PortSupplier::UnknownPort, CORBA::SystemException);" + NL + "" + NL + "        // CF::PropertySet" + NL + "        void configure(const CF::Properties&) throw (CORBA::SystemException, CF::PropertySet::InvalidConfiguration, CF::PropertySet::PartialConfiguration);" + NL + "" + NL + "        void query (CF::Properties & configProperties) throw (CF::UnknownProperties, CORBA::SystemException);" + NL + "" + NL + "        // CF::TestableObject" + NL + "        void runTest (CORBA::ULong TestID, CF::Properties & testValues) throw (CF::UnknownProperties, CF::TestableObject::UnknownTest, CORBA::SystemException);" + NL + "" + NL + "        // main omni_thread function" + NL + "        void run(void *args);" + NL + "" + NL + "" + NL + "" + NL + "    private:" + NL + "        // For component shutdown" + NL + "        omni_condition *component_running;" + NL + "        std::string comp_uuid;" + NL + "" + NL + "        bool component_alive;" + NL + "" + NL + "        std::string naming_service_name;" + NL + "        " + NL + "        ossie::ORB *orb;" + NL + "" + NL + "        // Threading stuff" + NL + "        omni_condition *data_in_signal;" + NL + "        omni_mutex data_in_signal_lock;" + NL + "        omni_mutex process_data_lock;" + NL + "        omni_mutex thread_exit_lock;" + NL + "        omni_mutex attribute_access;\t// used when modifying variables" + NL + "" + NL + "        // Functional members" + NL + "        // Housekeeping and data management variables" + NL + "        bool thread_started;" + NL + "};" + NL + "#endif";
  protected final String TEXT_9 = NL;

    /**
    * {@inheritDoc}
    */

    public String generate(Object argument)
  {
    final StringBuffer stringBuffer = new StringBuffer();
    
    TemplateParameter templ = (TemplateParameter) argument;
    ImplementationSettings implSettings = templ.getImplSettings();
    Implementation impl = templ.getImpl();
    SoftPkg softPkg = (SoftPkg) impl.eContainer();
    String PREFIX = gov.redhawk.ide.codegen.util.CodegenFileHelper.getPreferredFilePrefix(softPkg, implSettings);

    stringBuffer.append(TEXT_1);
    stringBuffer.append(PREFIX.toUpperCase());
    stringBuffer.append(TEXT_2);
    stringBuffer.append(PREFIX.toUpperCase());
    stringBuffer.append(TEXT_3);
    stringBuffer.append(PREFIX);
    stringBuffer.append(TEXT_4);
    stringBuffer.append(PREFIX);
    stringBuffer.append(TEXT_5);
    stringBuffer.append(TEXT_6);
    stringBuffer.append(PREFIX);
    stringBuffer.append(TEXT_7);
    stringBuffer.append(PREFIX);
    stringBuffer.append(TEXT_8);
    stringBuffer.append(TEXT_9);
    return stringBuffer.toString();
  }
} 