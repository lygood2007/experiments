// Test based on YouTube/Tin Can Tech Tips: http://tincanapi.com/2013/08/28/youtubetin-can-tech-tips/

var irpagnossin = irpagnossin || {};

// I don't know why, but YT.PlayerState is not available :s This is a work around.
irpagnossin.STATE = {
    UNSTARTED: -1,
    ENDED:      0,
    PLAYING:    1,
    PAUSED:     2,
    BUFFERING:  3,
    CUED:       5
}

TinCan.enableDebug();
// TinCan client
irpagnossin.tincan = new TinCan ({
    recordStores: LRS
});

irpagnossin.lastPlayerState = irpagnossin.STATE.UNSTARTED;
irpagnossin.video_data = null;
irpagnossin.lastPlayerTime = 0;
irpagnossin.statement = {
    actor: {
        name: "Ivan Ramos Pagnossin",
        openid: "http://www.google.com/profiles/ivan.pagnossin",
        objectType: "Agent"
    },
    verb: {
        id: "http://activitystrea.ms/schema/1.0/watch",
        display: {
            "en-US": "wathed",
            "pt-BR": "Assistiu"
        }
    },
    object: null
}

var VIDEO_ID = 0;
var videoTitle = "Nenhum";

irpagnossin.onPlayerStateChange = function (newState) {
    console.log(newState);

    switch (newState) {
        case (irpagnossin.STATE.PLAYING):
            console.log("Playing");
            irpagnossin.videoStarted();
            break;

        case (irpagnossin.STATE.PAUSED):
            console.log("Pause");
            if (irpagnossin.lastPlayerState == irpagnossin.STATE.PLAYING) {
                irpagnossin.videoWatched(irpagnossin.lastPlayerTime, ytplayer.getCurrentTime());
            } else if (irpagnossin.lastPlayerState == irpagnossin.STATE.PAUSED) {
                irpagnossin.videoSkipped(irpagnossin.lastPlayerTime, ytplayer.getCurrentTime());
            }
            irpagnossin.videoPaused();
            break;

        case (irpagnossin.STATE.ENDED):
            console.log("ended");
            irpagnossin.videoEnded();
            break;

        case (irpagnossin.STATE.UNSTARTED):
            break;
    }

    irpagnossin.lastPlayerTime = ytplayer.getCurrentTime();
    irpagnossin.lastPlayerState = newState;
}

irpagnossin.videoStarted = function () {
    console.log("video started");
}

irpagnossin.videoWatched = function (start, finish) {

    console.log("video watched");

    irpagnossin.statement.object.definition.name = {
        "pt-BR": videoTitle + " from " + timeString(start) + " to " + timeString(finish)
    };

    irpagnossin.statement.object.definition.extensions = {
        "http://demo.watershedlrs.com/tincan/extensions/start_point": timeString(start),
        "http://demo.watershedlrs.com/tincan/extensions/end_point": timeString(finish)
    };

    console.log("Enviando para LRS...");
    console.log(irpagnossin.statement);
    irpagnossin.tincan.sendStatement(irpagnossin.statement);
}

irpagnossin.videoPaused = function () {
    console.log("video paused");
}

irpagnossin.videoEnded = function () {
    console.log("video ended");
}

function timeString (time) {
    return time;
}


$(document).ready(function () {
    console.log("Início");

	VIDEO_ID = "wfc0MyTSj9c";	
    var params = { allowScriptAccess: "always" };
    var atts = { id: "myytplayer" };


    swfobject.embedSWF(
    	"http://www.youtube.com/v/" + VIDEO_ID + "?enablejsapi=1&playerapiid=ytplayer&version=3",
    	"ytapiplayer",
    	"425", "356", // width and height of video
    	"8", // Minimum version of Flash player
    	null,
    	null,
    	params,
    	atts);

    $.getJSON('https://gdata.youtube.com/feeds/api/videos/' + VIDEO_ID + '?v=2&alt=json',
        function (data) {

            videoTitle = data.entry.title.$t;
            console.log(videoTitle);
            irpagnossin.statement.object = {      
                id: "http://www.youtube.com/v/" + VIDEO_ID,
                objectType: "Activity",
                definition: {
                    name: {
                        "pt-BR": videoTitle // TODO: how to get locale?
                    },
                    description: {
                        "pt-BR": "From where?"
                    }
                }
            };
        }
    );
});

function onYouTubePlayerReady(playerId) {
    ytplayer = $("#myytplayer")[0];
    ytplayer.addEventListener("onStateChange", "irpagnossin.onPlayerStateChange");
}