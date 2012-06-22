<h1>mChatServer</h1>

<h2>About</h2>
<strong>mChatServer</strong> is the server part of the mChat chat system along with the client for the server. Both of these projects are something that I'm developing for a university course in paralell computing systems.

<h2>Functionality</h2>
The server has the following functionality:
<ul>
  <li><strong>The</strong> configuration of the server is read from a file that is called <strong>server.properties</strong>. The user can confgire the port at which the server will listen for requests</li> <br />
  <li><strong>The</strong> server uses the <strong>log4j</strong> logging utility for logging. This allows for tuning the amount of logging work done by specifying different settings in the <strong>log4j.properties</strong> file</li> <br />
  <li><strong>The</strong> server receives requests from users, handles them and afterwards writes responses.</li> <br />
  <li><strong>The</strong> server can be used for only interconnecting users so as to allow for a p2p transfer.</li>
</ul>

<h2>Supported protocol</h2>
<ul>
  <li><strong>user < username > < port_number ></strong> - registers the specified username within the server. No clients with the same username can be registered. Server responses will be written to the specified in the request port</li><br />
  <li><strong>send_to < username > < message > < port ></strong> - sends the specified message to the server. Server/client responses will be written to the specified port</li><br />
  <li><strong>send_al < message > < port_number ></strong> - broadcasts the message to all the registered clients. Server/client responses will be written to the specified port on the client</li></br />
  <li><strong>list</strong> - returns a list, containing the usernames of all the clients currently registered within the server.</li></br />
  <li><strong>send_file_to < username > < filepath > < port ></strong> - returns the address and the port at which the client that the file is intended for will be listening for messages. </li><br />
</ul>

<h2>License</h2>
The <strong>mChat</strong> server can be freely used and distributed under the terms of the GNU General Public License. For more information see the <strong>License</strong> file

<h2>Author</h2>
<strong>Martin Asenov Asenov</strong> <br />
email: asenov.m@gmail.com
