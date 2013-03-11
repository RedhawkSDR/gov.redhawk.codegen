from ossie.utils.sb import *
from omniORB import CORBA
from ossie.cf import ExtendedCF

domainless.setIDE_REF(CORBA.ORB_init().string_to_object("{0}")._narrow(ExtendedCF.Sandbox))
