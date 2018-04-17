from ossie.utils.sb import *
from omniORB import CORBA
from ossie.cf import ExtendedCF

domainless.setIDE_REF(CORBA.ORB_init().string_to_object("%1$s")._narrow(ExtendedCF.Sandbox))

from ossie.utils import sb
IDELocation("%2$s")
#DONE SETUP

