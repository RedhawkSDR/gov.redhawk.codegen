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
package gov.redhawk.ide.codegen.jet.java.template;

import gov.redhawk.ide.codegen.ImplementationSettings;
import gov.redhawk.ide.codegen.jet.java.JavaTemplateParameter;
import gov.redhawk.model.sca.util.ModelUtil;
import mil.jpeojtrs.sca.spd.Implementation;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.jdt.core.IClasspathEntry;
import mil.jpeojtrs.sca.spd.SoftPkg;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;

	/**
    * @generated
    */

public class StartJavaShTemplate
{

  protected static String nl;
  public static synchronized StartJavaShTemplate create(String lineSeparator)
  {
    nl = lineSeparator;
    StartJavaShTemplate result = new StartJavaShTemplate();
    nl = null;
    return result;
  }

  public final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "#!/bin/sh" + NL + "myDir=`dirname $0`" + NL + "" + NL + "# Setup the OSSIEHOME Lib jars on the classpath" + NL + "libDir=$OSSIEHOME/lib" + NL + "libFiles=`ls -1 $libDir/*.jar`" + NL + "for file in $libFiles" + NL + "do" + NL + "\tif [ x\"$CLASSPATH\" = \"x\" ]" + NL + "\tthen" + NL + "\t\texport CLASSPATH=$file" + NL + "\telse" + NL + "\t\texport CLASSPATH=$file:$CLASSPATH" + NL + "\tfi" + NL + "done" + NL + "" + NL + "# NOTE: the $@ must be quoted \"$@\" for arguments to be passed correctly" + NL + "" + NL + "#Sun ORB start line" + NL + "exec $JAVA_HOME/bin/java -cp ";
  protected final String TEXT_2 = ":";
  protected final String TEXT_3 = ":$myDir/";
  protected final String TEXT_4 = ".jar:$myDir/bin:$CLASSPATH ";
  protected final String TEXT_5 = " \"$@\"" + NL + "" + NL + "#JacORB start lines" + NL + "#$JAVA_HOME/bin/java -cp ";
  protected final String TEXT_6 = ":";
  protected final String TEXT_7 = ":$myDir/jacorb.jar:$myDir/antlr.jar:$myDir/avalon-framework.jar:$myDir/backport-util-concurrent.jar:$myDir/logkit.jar:$myDir/";
  protected final String TEXT_8 = ".jar:$myDir/bin:$CLASSPATH ";
  protected final String TEXT_9 = " \"$@\"";
  protected final String TEXT_10 = NL;

    /**
    * {@inheritDoc}
    */

    public String generate(Object argument)
  {
    final StringBuffer stringBuffer = new StringBuffer();
     
    JavaTemplateParameter template = (JavaTemplateParameter) argument;
    ImplementationSettings implSettings = template.getImplSettings();
    Implementation impl = template.getImpl();
    SoftPkg softPkg = (SoftPkg) impl.eContainer();
    IResource resource = ModelUtil.getResource(implSettings);
    IProject project = resource.getProject();
    IJavaProject javaProject = JavaCore.create(project);
    String implName = gov.redhawk.ide.codegen.util.CodegenFileHelper.safeGetImplementationName(impl, implSettings);
    String jarPrefix = gov.redhawk.ide.codegen.util.CodegenFileHelper.getPreferredFilePrefix(softPkg, implSettings);
    String pkg = template.getPackage();
    String mainClass = gov.redhawk.ide.codegen.jet.java.JavaGeneratorProperties.getMainClass(impl, implSettings);
    
    String projDir = "/" + project.getName() + "/" + implSettings.getOutputDir();
    String libs = "";
    String vars = "";
    try {
        for (final IClasspathEntry path : javaProject.getRawClasspath()) {
            if (path.getEntryKind() == IClasspathEntry.CPE_LIBRARY) {
                final String lib = path.getPath().toString();
                libs += lib.replaceAll(projDir, "\\$myDir") + ":";
            } else if (path.getEntryKind() == IClasspathEntry.CPE_VARIABLE) {
                vars += "$" + path.getPath().toString() + ":";
            }
        }
    } catch (JavaModelException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }

    stringBuffer.append(TEXT_1);
    stringBuffer.append(libs);
    stringBuffer.append(TEXT_2);
    stringBuffer.append(vars);
    stringBuffer.append(TEXT_3);
    stringBuffer.append(jarPrefix);
    stringBuffer.append(TEXT_4);
    stringBuffer.append(mainClass);
    stringBuffer.append(TEXT_5);
    stringBuffer.append(libs);
    stringBuffer.append(TEXT_6);
    stringBuffer.append(vars);
    stringBuffer.append(TEXT_7);
    stringBuffer.append(jarPrefix);
    stringBuffer.append(TEXT_8);
    stringBuffer.append(mainClass);
    stringBuffer.append(TEXT_9);
    stringBuffer.append(TEXT_10);
    return stringBuffer.toString();
  }
} 