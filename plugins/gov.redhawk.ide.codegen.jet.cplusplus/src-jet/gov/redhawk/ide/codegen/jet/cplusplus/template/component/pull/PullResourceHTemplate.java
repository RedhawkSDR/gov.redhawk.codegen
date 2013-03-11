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
package gov.redhawk.ide.codegen.jet.cplusplus.template.component.pull;

import gov.redhawk.ide.codegen.ImplementationSettings;
import gov.redhawk.ide.codegen.jet.TemplateParameter;
import gov.redhawk.ide.codegen.jet.cplusplus.CplusplusJetGeneratorPlugin;
import gov.redhawk.ide.idl.Operation;
import gov.redhawk.ide.idl.IdlUtil;
import gov.redhawk.ide.idl.Interface;
import gov.redhawk.ide.preferences.RedhawkIdePreferenceConstants;
import gov.redhawk.ide.RedhawkIdeActivator;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import mil.jpeojtrs.sca.scd.Provides;
import mil.jpeojtrs.sca.spd.Implementation;
import mil.jpeojtrs.sca.spd.SoftPkg;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.EList;

/**
 * @generated
 */
public class PullResourceHTemplate
{

  protected static String nl;
  public static synchronized PullResourceHTemplate create(String lineSeparator)
  {
    nl = lineSeparator;
    PullResourceHTemplate result = new PullResourceHTemplate();
    nl = null;
    return result;
  }

  public final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "#ifndef ";
  protected final String TEXT_2 = "_IMPL_H" + NL + "#define ";
  protected final String TEXT_3 = "_IMPL_H" + NL + "" + NL + "#include \"";
  protected final String TEXT_4 = "_base.h\"" + NL + "" + NL + "class ";
  protected final String TEXT_5 = "_i;" + NL + "" + NL + "class ";
  protected final String TEXT_6 = "_i : public ";
  protected final String TEXT_7 = "_base" + NL + "{" + NL + "    ENABLE_LOGGING" + NL + "    public:";
  protected final String TEXT_8 = " ";
  protected final String TEXT_9 = NL + "        ";
  protected final String TEXT_10 = "_i(const char *uuid, const char *label);";
  protected final String TEXT_11 = NL + "        ";
  protected final String TEXT_12 = "_i(char *devMgr_ior, char *id, char *lbl, char *sftwrPrfl);";
  protected final String TEXT_13 = NL + "        ";
  protected final String TEXT_14 = "_i(char *devMgr_ior, char *id, char *lbl, char *sftwrPrfl, char *compDev);";
  protected final String TEXT_15 = NL + "        ";
  protected final String TEXT_16 = "_i(char *devMgr_ior, char *id, char *lbl, char *sftwrPrfl, CF::Properties capacities);";
  protected final String TEXT_17 = NL + "        ";
  protected final String TEXT_18 = "_i(char *devMgr_ior, char *id, char *lbl, char *sftwrPrfl, CF::Properties capacities, char *compDev);";
  protected final String TEXT_19 = NL + "        ~";
  protected final String TEXT_20 = "_i();" + NL + "        int serviceFunction();";
  protected final String TEXT_21 = NL + "        std::string attach(const BULKIO::SDDSStreamDefinition& stream, const char* userid);" + NL + "        void detach(const char* userid);";
  protected final String TEXT_22 = NL + "};" + NL + "" + NL + "#endif";
  protected final String TEXT_23 = NL;

  public String generate(Object argument) throws CoreException
  {
    final StringBuffer stringBuffer = new StringBuffer();
    
    TemplateParameter templ = (TemplateParameter) argument;
    ImplementationSettings implSettings = templ.getImplSettings();
    Implementation impl = templ.getImpl();
    SoftPkg softPkg = (SoftPkg) impl.eContainer();
    String PREFIX = gov.redhawk.ide.codegen.util.CodegenFileHelper.getPreferredFilePrefix(softPkg, implSettings);
    
    boolean hasPushPacketCall = false;
    boolean hasSddsPort = false;
    List<IPath> search_paths = Arrays.asList(RedhawkIdeActivator.getDefault().getDefaultIdlIncludePath());
    EList<Provides> provides = softPkg.getDescriptor().getComponent().getComponentFeatures().getPorts().getProvides();
    HashSet<String> providesList = new HashSet<String>();
    for (Provides entry : provides) {
        String intName = entry.getRepID();
        providesList.add(intName);
        if (intName.contains("BULKIO/dataSDDS")) {
            hasSddsPort = true;
        }
    }
    for (String entry : providesList) {
        Interface iface = IdlUtil.getInstance().getInterface(search_paths, entry.split(":")[1], true);
        if (iface == null) {
            throw new CoreException(new Status(IStatus.ERROR, CplusplusJetGeneratorPlugin.PLUGIN_ID, "Unable to find interface for " + entry));
        }
        for (Operation op : iface.getOperations()) {
            int numParams = op.getParams().size();
            if ("pushPacket".equals(op.getName()) && (numParams == 4)) {
                hasPushPacketCall = true;
                break;
            }
        }
        if (hasPushPacketCall) {
            break;
        }
    }

    stringBuffer.append(TEXT_1);
    stringBuffer.append(PREFIX.toUpperCase());
    stringBuffer.append(TEXT_2);
    stringBuffer.append(PREFIX.toUpperCase());
    stringBuffer.append(TEXT_3);
    stringBuffer.append(PREFIX);
    stringBuffer.append(TEXT_4);
    stringBuffer.append(PREFIX);
    stringBuffer.append(TEXT_5);
    stringBuffer.append(PREFIX);
    stringBuffer.append(TEXT_6);
    stringBuffer.append(PREFIX);
    stringBuffer.append(TEXT_7);
     if (!templ.isDevice()) {
    stringBuffer.append(TEXT_8);
    stringBuffer.append(TEXT_9);
    stringBuffer.append(PREFIX);
    stringBuffer.append(TEXT_10);
     } else { 
    stringBuffer.append(TEXT_11);
    stringBuffer.append(PREFIX);
    stringBuffer.append(TEXT_12);
    stringBuffer.append(TEXT_13);
    stringBuffer.append(PREFIX);
    stringBuffer.append(TEXT_14);
    stringBuffer.append(TEXT_15);
    stringBuffer.append(PREFIX);
    stringBuffer.append(TEXT_16);
    stringBuffer.append(TEXT_17);
    stringBuffer.append(PREFIX);
    stringBuffer.append(TEXT_18);
    }
    stringBuffer.append(TEXT_19);
    stringBuffer.append(PREFIX);
    stringBuffer.append(TEXT_20);
    if (hasSddsPort) {
    stringBuffer.append(TEXT_21);
    }
    stringBuffer.append(TEXT_22);
    stringBuffer.append(TEXT_23);
    return stringBuffer.toString();
  }
}

// END GENERATED CODE