let express = require( 'express' );
let app = express();
let server = require( 'http' ).Server( app );
let io = require( 'socket.io' )( server );
// let stream = require( './ws/stream' );
let path = require( 'path' );
let favicon = require( 'serve-favicon' );
// let mongoose = require('mongoose');
 let mongo = require('mongodb').MongoClient;
 




app.use( favicon( path.join( __dirname, 'favicon.ico' ) ) );
app.use( '/assets', express.static( path.join( __dirname, 'assets' ) ) );

app.set('views', path.join(__dirname, 'views'));
app.set('view engine', 'ejs')



app.get( '/',  ( req, res ) => {
    console.log(req.query.room);
    const room = req.query.room;
    mongo.connect('mongodb://127.0.0.1/mongochat', async function(err, db){
        if(err){
            throw err;
        }
        
        const chat = await db.collection('chats').find({room: room}).toArray();
        
        
        res.render('index', {dataChat: chat});
        // res.sendFile( __dirname + '/index.html', {dataChat: chat} );
    })
   
} );


//Connect to mongodb
mongo.connect('mongodb://127.0.0.1/mongochat', function(err, db){
    if(err){
        throw err;
    }

    console.log('MongoDB connected...');

    io.on( 'connection', function(socket){
        socket.on( 'subscribe', ( data ) => {
            //subscribe/join a room
            socket.join( data.room );
            socket.join( data.socketId );
    
            //Inform other members in the room of new user's arrival
            if ( socket.adapter.rooms[data.room].length > 1 ) {
                socket.to( data.room ).emit( 'new user', { socketId: data.socketId } );
            }
    
            // console.log( socket.rooms );
        } );
    
    
        socket.on( 'newUserStart', ( data ) => {
            socket.to( data.to ).emit( 'newUserStart', { sender: data.sender } );
        } );
    
    
        socket.on( 'sdp', ( data ) => {
            socket.to( data.to ).emit( 'sdp', { description: data.description, sender: data.sender } );
        } );
    
    
        socket.on( 'ice candidates', ( data ) => {
            socket.to( data.to ).emit( 'ice candidates', { candidate: data.candidate, sender: data.sender } );
        } );

        let chat = db.collection('chats');

        // Get chats from mongo collection
        chat.find().limit(100).sort({_id:1}).toArray(function(err,res) {
                if(err){
                    throw err;
                }

                //emit the messages
                socket.emit('output', res);
        });
    
    
        socket.on( 'chat', ( data ) => {
            chat.insert({name: data.sender, message: data.msg, room: data.room, date: Date()}, ()=>{
                socket.to( data.room ).emit( 'chat', { sender: data.sender, msg: data.msg } );
            })
            
        });

    
        
    } );
    
});









server.listen( 3306, ()=>{
    console.log("connected to port: 3306")
} );

