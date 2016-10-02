/*

Copyright (C) 2013-2016, Securifera, Inc 

All rights reserved. 

Redistribution and use in source and binary forms, with or without modification, 
are permitted provided that the following conditions are met:

    * Redistributions of source code must retain the above copyright notice,
	this list of conditions and the following disclaimer.

    * Redistributions in binary form must reproduce the above copyright notice,
	this list of conditions and the following disclaimer in the documentation 
	and/or other materials provided with the distribution.

    * Neither the name of Securifera, Inc nor the names of its contributors may be 
	used to endorse or promote products derived from this software without specific
	prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS 
OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY 
AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER 
OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR 
CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR 
SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON 
ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT 
(INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, 
EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

================================================================================

The copyright on this package is held by Securifera, Inc

*/
package rsrc_edit.codec;

/**
 *
 * @author user
 */
public class ByteSpacer extends Codec{

    //===================================================================
    /**
     * 
     */
    public ByteSpacer() {
        super( BYTE_SPACER );
    }  

    //===================================================================
    /**
     * 
     * @param passedBytes
     * @return 
     */
    @Override
    public byte[] encode(byte[] passedBytes) {
        byte[] retBytes = new byte[passedBytes.length * 2];
        
        byte lw_Byte;
        byte hi_Byte;
        
        for( int i = 0,j=0; i < retBytes.length; i+=2,j++ ){
            byte aByte = passedBytes[j];
            lw_Byte = (byte) (aByte & 0xf);
            hi_Byte = (byte) ((aByte & 0xf0)>> 4 );
            retBytes[i] = hi_Byte;
            retBytes[i+1] = lw_Byte;            
        }
        
        return retBytes;
    }

    @Override
    public byte[] decode(byte[] passedBytes) {
        
        byte[] retBytes = new byte[passedBytes.length / 2];
        
        byte lw_Byte;
        byte hi_Byte;
        
        //Make it even
        int inputBufLen = passedBytes.length;
        inputBufLen -= inputBufLen % 2;
        
        for( int i = 0,j=0; i < inputBufLen; i+=2,j++ ){
            
            hi_Byte = passedBytes[i];
            lw_Byte = passedBytes[i+1];
                    
            retBytes[j] = (byte)((hi_Byte << 4) + lw_Byte);           
        }
        
        return retBytes;
    }
    
}
