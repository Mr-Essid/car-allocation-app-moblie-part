from net_config import net_, connect
from machine import Pin, UART, SoftI2C
from umqtt.robust import MQTTClient
import sys
import time






if not net_.isconnected():
    print('connecting ...')
    connect('test', '00000000')
    
    
broker_address = 'YOUR-BROKER-URI'
port_ = 8883  # Use 8883 for secure MQTT
client_id = 'YOUR-CLIENT-ID'
username = 'YOUR-USERNAME'
password_ = 'YOUR-PASSWORD'
ssl_param__ = {}




with open('CALL-IT-WHAT-YOU-WANT.pem', 'wb') as file:
    cert__ = file.read()


ssl_param__['cert'] = cert__
ssl_param__['server_hostname'] = broker_address





def convertToDegree(RawDegrees):

    RawAsFloat = float(RawDegrees)
    firstdigits = int(RawAsFloat/100)
    nexttwodigits = RawAsFloat - float(firstdigits*100)

    Converted = float(firstdigits + nexttwodigits/60.0)
    Converted = '{0:.6f}'.format(Converted)
    return str(Converted)





client__ = MQTTClient(client_id,
                      server = broker_address,
                      port=port_,
                      user=username,
                      password=password_,
                      ssl=True,
                      ssl_params = ssl_param__
     )


client__.connect()

buff_ = bytearray(255)
gpsModule = UART(2, baudrate=9600)

print(gpsModule)


while True:
    try:
        buff_ = gpsModule.readline()
        token = False
        values = []
        if buff_:
            array_ = str(buff_).split(',')
            # print(array_)
            
            if array_[0] == "b'$GPGLL" and len(array_) == 8:
                values.append(convertToDegree(array_[1]))
                values.append(convertToDegree(array_[3]))

                
                token = True
            if array_[0] == "b'$GPRMC" and len(array_) == 13:
        
                values.append(convertToDegree(array_[3]))
                values.append(convertToDegree(array_[5]))

        
                token = True
            if array_[0] == "b'$GPGGA" and len(array_) == 15:
                values.append(convertToDegree(array_[2]))
                values.append(convertToDegree(array_[4]))

                
        time.sleep(0.5)
        
        if len(values) == 2:
            client__.publish(b'/cars/car_ref/len', values[0].encode())
            client__.publish(b'/cars/car_ref/lat', values[1].encode())
            print('message sent')
            
    except KeyboardInterrupt as e:
        print(f'exit with keyboard')
        sys.exit(0)
    except Exception as e:
        client__.publish(b'/cars/car_ref/disconnect', 'car_ref')
        sys.exit(-1)
        
            
    
        
        
            
            

            
            
          
        
        
    
    



