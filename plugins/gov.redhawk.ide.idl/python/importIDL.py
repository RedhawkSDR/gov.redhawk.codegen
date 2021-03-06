from omniidl import idlast, idlvisitor, idlutil, main, idltype
from omniidl_be.cxx import types
import _omniidl
import os
try:
    from omniORB import URI, any, CORBA
except:
    import CORBA

#import base

keyList = range(34)
valList = ['null','void','short','long','ushort','ulong','float','double','boolean', \
           'char','octet','any','TypeCode','Principal','objref','struct','union','enum', \
           'string','sequence','array','alias','except','longlong','ulonglong', \
           'longdouble','wchar','wstring','fixed','value','value_box','native', \
           'abstract_interface','local_interface']
baseTypes = dict(zip(keyList,valList))

# Non-standard kinds for forward-declared structs and unions
baseTypes[100] = 'ot_structforward'
baseTypes[101] = 'ot_unionforward'

class Interface:
    def __init__(self,name,nameSpace="",operations=[],filename="",fullpath=""):
        self.name = name
        self.nameSpace = nameSpace
        self.attributes = []
        self.operations = []
        self.filename = filename    #does not include the '.idl' suffix
        self.fullpath = fullpath
        self.inherited_names = []
        self.inherited = []

    def __eq__(self,other):
        if isinstance(other, Interface):
            return (other.nameSpace == self.nameSpace ) and (other.name == self.name)
        else:
            return False

    def __ne__(self,other):
        if isinstance(other, Interface):
            return (other.nameSpace != self.nameSpace ) and (other.name != self.name)
        else:
            return True

    def __repr__(self):
        retstr = '{'
        retstr += "'name':'" + self.name + "',"
        retstr += "'nameSpace':'" + self.nameSpace + "',"
        retstr += "'filename':'" + self.filename + "',"
        retstr += "'fullpath':'" + self.fullpath + "',"
        retstr += "'attributes':["
        for attr in self.attributes:
            retstr += str(attr) + ','
        retstr += "],"
        retstr += "'operations':["
        for op in self.operations:
            retstr += str(op) + ','
        retstr += "]}"

        return retstr


class Operation:
    def __init__(self,name,returnType,params=[]):
        self.name = name
        self.returnType = returnType
        self.cxxReturnType = ('', 0)
        self.params = []
        self.raises = []

    def __repr__(self):
        retstr = '{'
        retstr += "'name':'" + self.name + "',"
        retstr += "'returnType':'" + self.returnType + "',"
        retstr += "'cxxReturnType':" + str(self.cxxReturnType) + ","
        retstr += "'params':["
        for p in self.params:
            retstr += str(p) + ','
#        retstr += ']}'
        retstr += '],'
        retstr += "'raises':["
        for r in self.raises:
            retstr += str(r) + ','
        retstr += ']}'

        return retstr

class Attribute:
    def __init__(self,name,readonly,dataType,returnType):
        self.name = name
        self.readonly = readonly
        self.dataType = dataType
        self.returnType = returnType
        self.cxxReturnType = ''
        self.cxxType = ''

    def __repr__(self):
        retstr = '{'
        retstr += "'name':'" + self.name + "',"
        retstr += "'readonly':'" + str(self.readonly) + "',"
        retstr += "'dataType':'" + self.dataType + "',"
        retstr += "'returnType':'" + self.returnType + "',"
        retstr += "'cxxReturnType':" + repr(self.cxxReturnType) + ","
        retstr += "'cxxType':'" + self.cxxType + "'"
        retstr += '}'

        return retstr

class Param:
    def __init__(self,name,dataType='',direction=''):
        """
        Exampleinterface complexShort {
            void pushPacket(in PortTypes::ShortSequence I, in PortTypes::ShortSequence Q);
        };
        """

        self.name = name            # The actual argument name: 'I'
        self.dataType = dataType    # The type of the argument: 'PortTypes::ShortSequence'
        self.cxxType = ""
        self.direction = direction  # Flow of data: 'in'

    def __repr__(self):
        retstr = '{'
        retstr += "'name':'" + self.name + "',"
        retstr += "'dataType':'" + self.dataType + "',"
        retstr += "'cxxType':'" + self.cxxType + "',"
        retstr += "'direction':'" + self.direction + "'}"

        return retstr

class Raises:
    def __init__(self, name):
        self.name = name

    def __repr__(self):
        retstr = '{'
        retstr += "'name':'" + self.name + "'}"

        return retstr

class ExampleVisitor (idlvisitor.AstVisitor):
    def __init__(self,*args):
        self.myInterfaces = []   #Used to store the interfaces that are found
        if hasattr(idlvisitor.AstVisitor,'__init__'):
            idlvisitor.AstVisitor.__init__(self,args)

    def visitAST(self, node):
        for n in node.declarations():
            n.accept(self)

    def visitModule(self, node):
        for n in node.definitions():
            n.accept(self)

    def visitInterface(self, node):
        if not node.mainFile():
            return

        #new_int = base.Interface(node.identifier(),node.scopedName()[0])
        #print "Node Identifier: " + node.identifier() + " , Scoped Node Name: " + node.scopedName()[0]
        new_int = Interface(node.identifier(),node.scopedName()[0])

        ops_list = []
        attrs_list = []
        self.addOps(node,ops_list,attrs_list)
        new_int.operations.extend(ops_list)
        new_int.attributes.extend(attrs_list)
        #print node.identifier() + " has " + str(len(new_int.operations)) + " operations"
        #print node.identifier() + " has " + str(len(new_int.attributes)) + " attributes"

        # find and store any inheritances
        for i in node.inherits():
            if (i.scopedName()[0],i.identifier()) not in new_int.inherited:
                new_int.inherited_names.append((i.scopedName()[0],i.identifier()))

        self.myInterfaces.append(new_int)

    def addOps(self,node,ops,attrs):

        # add inherited operations
        for i in node.inherits():
            self.addOps(i,ops,attrs)

        for d in node.contents():
            if isinstance(d, idlast.Operation):
                # create the Operation object
                #new_op = base.Operation(d.identifier(),baseTypes[d.returnType().kind()])
                kind = d.returnType().kind()
                if (kind==idltype.tk_alias): # resolve the 'alias'
                    kind = d.returnType().decl().alias().aliasType().kind()
                new_op = Operation(d.identifier(),baseTypes[kind])

                # Get the c++ mapping of the return type
                cxxRT = types.Type(d.returnType())
                #if not new_op.cxxReturnType == 'void':
                new_op.cxxReturnType = (cxxRT.base(), cxxRT.variable())

                #print new_op.name + "::" + d.identifier() + "()"
                #tmpstr = node.identifier() + "::" + d.identifier() + "("
                #tmpstr2 = "  " + node.identifier() + "::" + d.identifier() + "("

                # find and process the parameters of the operation
                if hasattr(d,'parameters'):
                    for p in d.parameters():
                        #new_param = base.Param(p.identifier())
                        new_param = Param(p.identifier())
                        t =  p.paramType()
                        # Get the c++ mapping of the type
                        cxxT = types.Type(t)
                        new_param.cxxType = cxxT.op(types.direction(p))

                        if hasattr(t,'scopedName'):
                            new_param.dataType = idlutil.ccolonName(t.scopedName())
                        else:
                            if isinstance(t,idltype.Type):
                                new_param.dataType = baseTypes[t.kind()]

                        if p.is_in() and p.is_out():
                            new_param.direction = 'inout'
                        elif p.is_out():
                            new_param.direction = 'out'
                        else:
                            new_param.direction = 'in'
                        new_op.params.append(new_param)

                if hasattr(d, 'raises'):
                    for r in d.raises():
                        #print r.identifier()
                        new_raises = Raises(r.identifier())
                        new_op.raises.append(new_raises)

                ops.append(new_op)
            if isinstance(d, idlast.Attribute):
                # create the Attribute object
                decl = d.declarators()[0]
                kind = d.attrType().kind()
                if (kind==idltype.tk_alias): # resolve the 'alias'
                    kind = d.attrType().decl().alias().aliasType().kind()
                if hasattr(d.attrType(),'scopedName'):
                    dataType = idlutil.ccolonName(d.attrType().scopedName())
                else:
                    dataType = baseTypes[kind]
                new_attr = Attribute(decl.identifier(),d.readonly(),dataType,baseTypes[kind])

                # Get the c++ mapping of the return type
                cxxRT = types.Type(d.attrType())
                new_attr.cxxReturnType = (cxxRT.base(), cxxRT.variable())
                new_attr.cxxType = cxxRT.op(0)

                attrs.append(new_attr)

def run(tree, args):
    visitor = ExampleVisitor()
    tree.accept(visitor)
    return visitor.myInterfaces

def getInterfacesFromFile(filename, includepath=None):
    popen_cmd = main.preprocessor_cmd
    if includepath:
        for newpath in includepath:
            popen_cmd += ' -I "' + newpath + '"'
    popen_cmd += ' "' + filename + '"'
    f = os.popen(popen_cmd, 'r')
    try:
        tree = _omniidl.compile(f)
    except TypeError:
        tree = _omniidl.compile(f, filename)

    try:
        ints = run(tree,'')
    except Exception, e:
        print e
        ints = []
        #print popen_cmd
        #print filename
        pass
    f.close()
    del tree
    idlast.clear()
    idltype.clear()
    _omniidl.clear()
    #store file name and location information for each interface
    for x in ints:
        x.fullpath = filename
        i = filename.rfind("/")
        if i >= 0:
            x.filename = filename[i+1:]

        x.filename = x.filename[:-4] #remove the .idl suffix

    return ints

def getInterfacesFromFileAsString(filename, includepath=None):
    ints = getInterfacesFromFile(filename, includepath)
    ifaces = ""
    for x in ints:
        ifaces = ifaces + str(x) + "\n"

    return ifaces

def importStandardIdl(std_idl_path='/usr/local/share/idl/ossie'):

    std_idl_include_path = '/usr/local/share/idl'
    # find where ossie is installed
    if 'OSSIEHOME' in os.environ and os.path.exists(os.environ['OSSIEHOME']):
        std_idl_path = os.path.join(os.environ['OSSIEHOME'], 'share/idl')
        std_idl_include_path = os.path.join(os.environ['OSSIEHOME'], 'share/idl')


    # normalize the path names
    std_idl_path = os.path.normpath(std_idl_path)

    if not os.path.exists(std_idl_path):
        print "Cannot find OSSIE installation location:\n" + std_idl_path
        return

    # list to hold IDL files
    idlList = []

    # list to hold any include paths the parser may need
    includePaths = [std_idl_include_path]

    # Add the CF interfaces first - in case another file includes them, we
    # don't want them asscociated with anything other than cf.idl
    cfdir = os.path.join(std_idl_path, "ossie/CF")
    if not os.path.isdir(cfdir):
        print "Cannot find CF idl files in the OSSIE installation location:\n" + cfdir

    for file in os.listdir(cfdir):
        if os.path.splitext(file)[1] == '.idl':
            if file not in idlList:
                idlList.append(os.path.join(cfdir,file))

    # search for .idl files recursively
    for dirpath, dirs, files in os.walk(std_idl_path):
        for f in files:
            if os.path.splitext(f)[1] == '.idl':
                idlList.append(os.path.join(dirpath, f))

    if len(idlList) <= 0:
        tmpstr = "Can't find any files in: " + std_idl_path
        print tmpstr
        return

#    # Add the CF interfaces first - in case another file includes them, we
#    # don't want them asscociated with anything other than cf.idl
    Available_Ints = []

    for idl_file in idlList:
        basename_idl_file = os.path.basename(idl_file)
        # standardIdl files are not included because they are aggregates of the other interfaces
        if 'standardIdl' in basename_idl_file:
            continue

        # customInterfaces files are not included because they are aggregates of the other interfaces
        if 'customInterfaces' in basename_idl_file:
            continue

        tempInts = getInterfacesFromFile(idl_file, includePaths)
        for t in tempInts:
            if t not in Available_Ints:
                Available_Ints.append(t)

    # correlate inherited interfaces
    for int1 in Available_Ints:
        for ns, inherited_int in int1.inherited_names:
            for int2 in Available_Ints:
                if ns == int2.nameSpace and inherited_int == int2.name:
                    int1.inherited.append(int2)
                    continue

    return Available_Ints


if __name__ == '__main__':
    # parse args
    from optparse import OptionParser
    parser = OptionParser()
    parser.add_option("--string", dest="string", default=False, action="store_true",
        help="Return interfaces as a string.")
    parser.add_option("-f", dest="filepath", default=None,
        help="File to parse.")
    parser.add_option("-i", dest="include", default=None,
        help="Comma-separated list of include paths.")
    parser.add_option("--cpp", dest="cpp", default=False, action="store_true",
        help="Return C++ language mappings.")

    (options, args) = parser.parse_args()

    if not options.filepath:
        parser.error("Must have a file")

    # The lack of a forward declaration defintion
    # isn't a big deal for importIDL; this allows us to
    # parse poa.idl and poa_include.idl without any 
    # errors or warnings.
    _omniidl.noForwardWarning()
    
    if options.string:
        includepaths = None
        if options.include:
            includepaths = [x for x in options.include.split(",") if x]

        print getInterfacesFromFileAsString(options.filepath, includepaths)
