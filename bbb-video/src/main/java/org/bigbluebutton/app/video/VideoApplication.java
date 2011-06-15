/**
* BigBlueButton open source conferencing system - http://www.bigbluebutton.org/
*
* Copyright (c) 2010 BigBlueButton Inc. and by respective authors (see below).
*
* This program is free software; you can redistribute it and/or modify it under the
* terms of the GNU Lesser General Public License as published by the Free Software
* Foundation; either version 2.1 of the License, or (at your option) any later
* version.
*
* BigBlueButton is distributed in the hope that it will be useful, but WITHOUT ANY
* WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A
* PARTICULAR PURPOSE. See the GNU Lesser General Public License for more details.
*
* You should have received a copy of the GNU Lesser General Public License along
* with BigBlueButton; if not, see <http://www.gnu.org/licenses/>.
* 
*/
package org.bigbluebutton.app.video;

import java.util.HashMap;
import java.util.Map;

import org.red5.logging.Red5LoggerFactory;
import org.red5.server.adapter.MultiThreadedApplicationAdapter;
import org.red5.server.api.IConnection;
import org.red5.server.api.IScope;
import org.red5.server.api.Red5;
import org.red5.server.api.stream.IBroadcastStream;
import org.red5.server.api.stream.IServerStream;
import org.red5.server.stream.ClientBroadcastStream;
import org.slf4j.Logger;
import org.red5.server.api.stream.IBroadcastStream;
import org.red5.server.stream.ClientBroadcastStream;

public class VideoApplication extends MultiThreadedApplicationAdapter {
	private static Logger log = Red5LoggerFactory.getLogger(VideoApplication.class, "video");
	
	private IScope appScope;
	private IServerStream serverStream;
	
	private boolean recordVideoStream = false;
	private EventRecordingService recordingService;
	
	
    @Override
	public boolean appStart(IScope app) {
	    super.appStart(app);
		log.info("oflaDemo appStart");
		System.out.println("oflaDemo appStart");    	
		appScope = app;
		return true;
	}

    @Override
	public boolean appConnect(IConnection conn, Object[] params) {
		log.info("oflaDemo appConnect"); 
		return super.appConnect(conn, params);
	}

    @Override
	public void appDisconnect(IConnection conn) {
		log.info("oflaDemo appDisconnect");
		if (appScope == conn.getScope() && serverStream != null) {
			serverStream.close();
		}
		super.appDisconnect(conn);
	}
    
    @Override
    public void streamPublishStart(IBroadcastStream stream) {
    	super.streamPublishStart(stream);
        if (recordVideoStream) {
	    	recordStream(stream);
        }
    }
    
    @Override
    public void streamBroadcastStart(IBroadcastStream stream) {
    	IConnection conn = Red5.getConnectionLocal();  
    	super.streamBroadcastStart(stream);
    	log.info("streamBroadcastStart " + stream.getPublishedName() + " " + System.currentTimeMillis() + " " + conn.getScope().getName());
		Map<String, String> event = new HashMap<String, String>();
		event.put("module", "WEBCAM");
		event.put("timestamp", new Long(System.currentTimeMillis()).toString());
		event.put("meetingId", conn.getScope().getName());
		event.put("stream", stream.getPublishedName());
		event.put("eventName", "StartWebcamShareEvent");
		
		recordingService.record(conn.getScope().getName(), event);
    }

    @Override
    public void streamBroadcastClose(IBroadcastStream stream) {
    	IConnection conn = Red5.getConnectionLocal();  
    	super.streamBroadcastClose(stream);
    	log.info("streamBroadcastClose " + stream.getPublishedName() + " " + System.currentTimeMillis() + " " + conn.getScope().getName());
		Map<String, String> event = new HashMap<String, String>();
		event.put("module", "WEBCAM");
		event.put("timestamp", new Long(System.currentTimeMillis()).toString());
		event.put("meetingId", conn.getScope().getName());
		event.put("stream", stream.getPublishedName());
		event.put("eventName", "StopWebcamShareEvent");
		
		recordingService.record(conn.getScope().getName(), event);
    }
    
    /**
     * A hook to record a stream. A file is written in webapps/video/streams/
     * @param stream
     */
    private void recordStream(IBroadcastStream stream) {
    	IConnection conn = Red5.getConnectionLocal();   
    	long now = System.currentTimeMillis();
    	String recordingStreamName = stream.getPublishedName() + "-" + now;
     
    	try {    		
    		log.info("Recording stream " + recordingStreamName );
    		ClientBroadcastStream cstream = (ClientBroadcastStream) this.getBroadcastStream(conn.getScope(), stream.getPublishedName() );
    		cstream.saveAs(recordingStreamName, false);
    	} catch(Exception e) {
    		log.error("ERROR while recording stream " + e.getMessage());
    		e.printStackTrace();
    	}    	
    }

	public void setRecordVideoStream(boolean recordVideoStream) {
		this.recordVideoStream = recordVideoStream;
	}
	
	public void setEventRecordingService(EventRecordingService s) {
		recordingService = s;
	}
}
