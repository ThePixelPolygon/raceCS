import React from 'react';
import { withTranslation } from 'react-i18next';

class Stations extends React.Component {
    renderStations() {
        let els = [];

        let stations = [];
        for (let station of Object.keys(this.props.stationData)) {
            stations.push({
                code: station,
                name: this.props.stationData[station].name
            });
        }
        stations.sort((first, second) => {
            return first.name > second.name ? 1 : -1;
        });

        for (let station of stations) {
            els.push(<div className="stationsGridInnerWrapper" key={station.code}>
                <div className="stationsGrid">
                    <div className="sectionHeader" style={{gridArea: "header"}}>{station.name}</div>

                    {this.renderStation(station.code)}
                </div>
            </div>)
        }
        return els;

        
    }

    renderStation(station) {
        let els = [];

        for (let username of Object.keys(this.props.playerData)) {
            let user = this.props.playerData[username];
            if (user.visited.includes(station)) {
                let clickHandler = () => {
                    this.props.onPlayerClicked({
                        username: username,
                        uuid: user.uuid
                    });
                };
                els.push(<div className="leaderboardGridItem" key={`${username}-image`} onClick={clickHandler}><img height="30" src={`https://crafatar.com/avatars/${user.uuid}?overlay=true`}></img></div>);
                els.push(<div className="leaderboardGridItem" key={`${username}-username`} onClick={clickHandler}>{username}</div>)
            }
        }

        if (els.length == 0) {
            els.push(<div style={{justifySelf: "center", alignSelf: "center", padding: "20px"}} key="noArrivals">{this.props.t("STATIONS_NO_ARRIVALS")}</div>)
        }

        return els;
    }


    render() {
        if (Object.keys(this.props.playerData).length === 0) {
            return <div className="mainView">
                <div className="errorContainer">
                    <h1>{this.props.t("NO_RACE")}</h1>
                    <p>{this.props.t("JOIN_RACE_PROMPT")}</p>
                </div>
            </div>
        } else {
            return <div className="mainView" style={{padding: "10px", flexDirection: "row"}}>
                <div className="stationsGridWrapper">
                    {this.renderStations()}
                </div>
            </div>
        }
    }
};

export default withTranslation()(Stations);