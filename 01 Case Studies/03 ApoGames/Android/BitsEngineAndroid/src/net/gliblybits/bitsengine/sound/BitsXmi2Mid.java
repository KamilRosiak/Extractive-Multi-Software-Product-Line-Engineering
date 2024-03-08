
/*
 * Copyright 2013 Marc Wiedenhoeft - GliblyBits
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.gliblybits.bitsengine.sound;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;

import net.gliblybits.bitsengine.utils.BitsLog;

import com.leff.midi.MidiFile;
import com.leff.midi.MidiTrack;
import com.leff.midi.event.Controller;
import com.leff.midi.event.ProgramChange;
import com.leff.midi.event.meta.Tempo;
import com.leff.midi.event.meta.TimeSignature;

public final class 
BitsXmi2Mid {

	private static final int XMI_TAG_FORM = 1179603533;
	private static final int XMI_TAG_XDIR = 1480870226;
	private static final int XMI_TAG_INFO = 1229866575;
	private static final int XMI_TAG_CAT  = 1128354848;
	private static final int XMI_TAG_XMID = 1481460036;
	private static final int XMI_TAG_EVNT = 1163284052;
//	private int 							mTrackCount = 0;
	
	public static final boolean 
	convert( 
			final byte[] xmiData, 
			final float tempo, 
			final String midFile ) {
		
		if( xmiData == null ) {
			BitsLog.e("BitsXmi2Mid - convert", "The given xmiData or midFile is null.");
			return false;
		}
		if( midFile.equals("") == true ) {
			BitsLog.e("BitsXmi2Mid - convert", "The given midFile is null.");
			return false;
		}
		if( tempo <= 0) {
			BitsLog.e("BitsXmi2Mid - convert", "The given tempo is <= 0.");
			return false;
		}
		
		final ArrayList<BitsXmiNote> notes = new ArrayList<BitsXmiNote>();
		final ByteBuffer buffer = ByteBuffer.wrap(xmiData);
		boolean done = false;
		
		while( true ) {
			if( done == true ) {
				break;
			}
			
			
			int chunkTag 	= buffer.getInt();
			int chunkLength = buffer.getInt();
			int chunkType 	= buffer.getInt();
			
			//read number of tracks in XMI file
			if( chunkTag == XMI_TAG_FORM && chunkType == XMI_TAG_XDIR ) { //"FORM" and "XDIR"
				chunkTag = buffer.getInt(); //"INFO"
				chunkLength = buffer.getInt(); //length
				
				if( chunkTag == XMI_TAG_INFO ) {
					buffer.getShort();//this.mTrackCount =  BitsUtils.toLittleEndian(this.mBuffer.getShort()); //number of tracks in file
				}
				
				continue; //move on
			}
			
			if( chunkTag == XMI_TAG_CAT && chunkType == XMI_TAG_XMID ) { //"CAT " and "XMID"
				continue; //move on
			}

			if( chunkTag == XMI_TAG_FORM && chunkType == XMI_TAG_XMID ) { //"FORM" and "XMID"
//				for( int t = 0; t < this.mTrackCount; t++ ) { //cycle through the tracks
					while( true ) { //read [TIMB], [RBRN], ..., EVNT
						chunkTag 	= buffer.getInt(); //chunk
						chunkLength = buffer.getInt(); //length
						
						if( chunkTag != XMI_TAG_EVNT ) {
							buffer.position( buffer.position() + chunkLength ); //skip this chunk!
						} else { //last and only "EVNT" chunk
							buffer.position( buffer.position() + 4 ); chunkLength -= 4; //unknown
							
							int time = 0;		
							while(chunkLength > 0) {
								int eventType = buffer.get() & 0xFF; chunkLength--;
								
								if( (eventType & 0x80) != 0) { //is value > 127 -> type
									
									BitsXmiNote note = new BitsXmiNote();
									note.mTimeIndex = time;
									note.mType = eventType;
									
									switch (eventType & 0xF0) {
										case 0x80:
										case 0x90:
										case 0xA0:
										case 0xB0:
										case 0xE0:
											note.mData1 = buffer.get() & 0xFF; chunkLength--;
											note.mData2 = buffer.get() & 0xFF; chunkLength--;
											if (0x90 == (eventType & 0xF0)) {
												int data1 = note.mData1;
												notes.add(note);
												
												note = new BitsXmiNote();
												note.mType = eventType;
												
												int eventLength = 0;
												eventType = buffer.get() & 0xFF; chunkLength--;
												while((eventType & 0x80) != 0) {
													eventLength = (eventLength << 7) | (eventType & 0x7f);
													eventType = buffer.get() & 0xFF; chunkLength--;
												}
												eventLength = (eventLength << 7) | (eventType & 0x7f);

												note.mTimeIndex = time + eventLength;
												note.mData1 = data1;
												note.mData2 = 0;
											}
											break;
										case 0xC0:
										case 0xD0:	
											note.mData1 = buffer.get() & 0xFF; chunkLength--;
											note.mData2 = 0;
											break;							
										case 0xF0:
											if (0xFF == eventType) {
												note.mData1 = buffer.get() & 0xFF; chunkLength--;
												note.mData2 = buffer.get() & 0xFF; chunkLength--;
												if (0x51 == note.mData1) {
													int tempo2 = 0;
													for (int i = 0; i < note.mData2; i++) {
														tempo2 = tempo2 << 8;
														int temp = buffer.get() & 0xFF; chunkLength--;
														tempo2 |= temp;
													}

//													if (0 == tempo) {
//														tempo = tempo2;
//													}
												} else {
													chunkLength -= note.mData2;
												}
											}
											break;
									}
									
									notes.add(note);
								} else {
									time += eventType;
								}
							}
							buffer.clear();
							done = true;
							break; //stop while-loop
						}
					}
//				}
			}
		}
		
        final MidiTrack tempoTrack = new MidiTrack();
        final MidiTrack noteTrack = new MidiTrack();
        final TimeSignature ts = new TimeSignature();
        ts.setTimeSignature(4, 4, TimeSignature.DEFAULT_METER, TimeSignature.DEFAULT_DIVISION);
        final Tempo t = new Tempo();
        t.setBpm(tempo);
        tempoTrack.insertEvent(ts);
        tempoTrack.insertEvent(t);

        int oldTime = 0;
        int oldVelocity = 0;
        final ArrayList<BitsXmiNote> noteList = notes;
        final int size = notes.size();
        for(int i = 0; i < size; i++) {
        	final BitsXmiNote note = noteList.get(i);
        	
        	//0xA0: Polyphonic key pressure
//        	if( (note.mType & 0xF0) == 160 ) {
//        		
//        	}
        	
        	//0xB0: Controller
        	if( (note.mType & 0xF0) == 176 ) {
        		final Controller c = new Controller(note.mTimeIndex, (note.mType - (note.mType & 0xF0)), note.mData1, note.mData2);
        		noteTrack.insertEvent(c);
        		continue;
        	}
        	
        	//0xC0: Instrument change
        	if( (note.mType & 0xF0) == 192 ) {
        		final ProgramChange pc = new ProgramChange(note.mTimeIndex, (note.mType - (note.mType & 0xF0)), note.mData1);
        		noteTrack.insertEvent(pc);
        		continue;
        	}
        	
        	//0x90: Note on || 0x80: Note off -> data2 == 0!
            if( (note.mType & 0xF0) == 144 ) {
              	if( (note.mData2 & 0xF0) != 0 ) {
               		oldTime = note.mTimeIndex;
               		oldVelocity = note.mData2;
               	} else {
                   	noteTrack.insertNote( (note.mType - (note.mType & 0xF0)), note.mData1, oldVelocity, oldTime, note.mTimeIndex - oldTime);
               	}
              	continue;
            }
        }
        
        final ArrayList<MidiTrack> tracks = new ArrayList<MidiTrack>();
        tracks.add(tempoTrack);
        tracks.add(noteTrack);
        
        final MidiFile midi = new MidiFile(MidiFile.DEFAULT_RESOLUTION, tracks);
        
        File output = new File( midFile );
        try {
        	midi.writeToFile(output);
        } catch(IOException e) {
        	BitsLog.e( e, "BitsXmi2Mid - convert", "Unable to save the midi file: " + midFile);
        	return false;
        }
        
        return true;
	}
}
