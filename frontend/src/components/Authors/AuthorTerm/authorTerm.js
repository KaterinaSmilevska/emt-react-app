import React from "react";

const authorTerm = (props) => {
    return (
        <tr>
            <td scope={"col"}>{props.term.name}</td>
            <td scope={"col"}>{props.term.surname}</td>
            <td scope={"col"}>{props.term.country.name}</td>
        </tr>

    );
}

export default authorTerm;