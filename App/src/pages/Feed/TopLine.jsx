import { useMemo } from "react";
import "./TopLine.css";

function reload() {
	window.location.reload();
}

function hiddenUsername(name) {
	const len = name.length;
	return name.slice(0, 2 - (len < 7)) + "***" + (len === 5 ? "*" : "") + name.slice(len - 2 + (len === 5), len)
}

export default function TopLine({ page, username, avatar }) {
	const shownUsername = useMemo(() => hiddenUsername(username), [ username ])
	
	if(page !== "feed" && page !== "auth") return <></>;
	
	return (<div className="top-line">
		<div className="app-title">
			<h1 onClick={reload} className="app-name">
				Clipster
			</h1>
			<div className="beta-tag">
				<a>BETA</a>
			</div>
		</div>
		{
	 	 page === "auth" ? <></> :
		 <div className="user-tag">
			<a className="hidden-tag">
				@{ shownUsername }
			</a>
			<div className="user-avatar" style={{ backgroundImage: avatar }}/>
		 </div>
		}
	</div>)
}
