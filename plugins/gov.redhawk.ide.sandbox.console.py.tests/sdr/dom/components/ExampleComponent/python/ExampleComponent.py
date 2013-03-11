#!/usr/bin/env python 
#
# AUTO-GENERATED
#
# Source: ExampleComponent.spd.xml
# Generated on: Mon Jun 25 14:28:45 EDT 2012
from ossie.resource import Resource, start_component
import logging
import math
import random
import sys

from ExampleComponent_base import * 

class ExampleComponent_i(ExampleComponent_base):
    """<DESCRIPTION GOES HERE>"""
    def initialize(self):
        """
        This is called by the framework immediately after your component registers with the NameService.
        
        In general, you should add customization here and not in the __init__ constructor.  If you have 
        a custom port implementation you can override the specific implementation here with a statement
        similar to the following:
          self.some_port = MyPortImplementation()
        """
        ExampleComponent_base.initialize(self)
        # TODO add customization here.

        # Autostart the Resource if necessary
        if self.auto_start:
            self.start()
        
        self.stream_id = str(uuid.uuid4())
        self.sri = BULKIO.StreamSRI(1, 0.0, 0.0, BULKIO.UNITS_TIME, 0, 0.0, 0.0, BULKIO.UNITS_NONE, 0, self.stream_id, False, [])
        self.sriUpdate = True
        

    def process(self):
          
        data, T, EOS, in_streamID, in_sri, in_sriChanged, inputQueueFlushed = self.port_inputPort.getPacket()
        if data is not None:
            print "Received data", data
            sys.stdout.flush()
            
        
        if self.sriUpdate:
            self.port_outputPort.pushSRI(self.sri);
        
        tmp_time = time.time()
        wsec = math.modf(tmp_time)[1]
        fsec = math.modf(tmp_time)[0]
        tstamp = BULKIO.PrecisionUTCTime(BULKIO.TCM_CPU, BULKIO.TCS_VALID, 0, wsec, fsec)    
            
        outData = [float(1000*random.random()) for i in xrange(self.samples)]
        
        self.port_outputPort.pushPacket(outData, tstamp, False, self.stream_id)

        try:
            time.sleep(1)
        except:
            pass

        return NORMAL
        
  
if __name__ == '__main__':
    logging.getLogger().setLevel(logging.WARN)
    logging.debug("Starting Component")
    start_component(ExampleComponent_i)