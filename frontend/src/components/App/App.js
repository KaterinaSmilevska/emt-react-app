import './App.css';
import React, {Component} from "react";
import {BrowserRouter as Router, Redirect, Route} from 'react-router-dom'
import Countries from "../Countries/countries";
import Authors from "../Authors/AuthorList/authors";
import Categories from  "../Categories/categories";
import Books from "../Books/BookList/books";
import Header from "../Header/header";
import BookAdd from "../Books/BookAdd/bookAdd";
import BookEdit from "../Books/BookEdit/bookEdit";
import libraryService from "../../repository/libraryRepository";

class App extends Component{

    constructor(props) {
        super(props);
        this.state = {
            countries: [],
            authors: [],
            categories: [],
            books: [],
            selectedBook: {}
        }
    }

    render() {
        return (
            <Router>
                <Header/>
                <main>
                    <div className="container">
                        <Route path={"/countries"} exact render={() =>
                            <Countries countries={this.state.countries}/>}/>
                        <Route path={"/authors"} exact render={() =>
                            <Authors authors={this.state.authors}/>}/>
                        <Route path={"/categories"} exact render={() =>
                            <Categories categories={this.state.categories}/>}/>
                        <Route path={"/books/add"} exact render={() =>
                            <BookAdd categories={this.state.categories}
                                     authors={this.state.authors}
                                     onAddBook={this.addBook}/>}/>
                        <Route path={"/books/edit/:id"} exact render={() =>
                            <BookEdit categories={this.state.categories}
                                      authors={this.state.authors}
                                      onEditBook={this.editBook}
                                    book={this.state.selectedBook}/>}/>
                        <Route path={"/books"} exact render={() =>
                            <Books books={this.state.books}
                                   onDelete={this.deleteBook}
                                    onEdit={this.getBook}
                                    onIsTaken={this.onIsTaken}/>}/>
                        <Redirect to={"/books"}/>
                    </div>
                </main>
            </Router>
        )
    }

    componentDidMount() {
        this.fetchData();
    }

    fetchData = () => {
        this.loadCountries();
        this.loadAuthors();
        this.loadCategories();
        this.loadBooks();
    }

    loadCountries = () => {
        libraryService.fetchCountries()
            .then((data) => {
                this.setState( {
                    countries: data.data
                })
            })
    }

    loadAuthors = () => {
        libraryService.fetchAuthors()
            .then((data) => {
                this.setState( {
                    authors: data.data
                })
            })
    }

    loadCategories = () => {
        libraryService.fetchCategories()
            .then((data) => {
                this.setState( {
                    categories: data.data
                })
            })
    }

    loadBooks = () => {
        libraryService.fetchBooks()
            .then((data) => {
                this.setState( {
                    books: data.data
                })
            })
    }

    deleteBook = (id) => {
        libraryService.deleteBook(id)
            .then(() => {
                this.loadBooks();
            })
    }

    addBook = (name, category, author, availableCopies) => {
        libraryService.addBook(name, category, author, availableCopies)
            .then(() => {
                this.loadBooks();
            })
    }

    getBook = (id) => {
        libraryService.getBook(id)
            .then((data) => {
                this.setState({
                    selectedBook: data.data
                })
            })
    }

    editBook = (id, name, category, author, availableCopies) => {
        libraryService.editBook(id, name, category, author, availableCopies)
            .then(() => {
                this.loadBooks();
            })
    }

    onIsTaken = (id) => {
        libraryService.isTaken(id)
            .then(() => {
                this.loadBooks();
            })
    }
}

export default App;
