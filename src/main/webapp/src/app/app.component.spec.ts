import {
    it,
    inject,
    beforeEachProviders
} from 'angular2/testing';

// to use Translate Service, we need Http, and to test Http we need to mock the backend
import {BaseRequestOptions, Http} from 'angular2/http';
import {MockBackend} from 'angular2/http/testing';
import {provide} from "angular2/core";

// Load the implementations that should be tested
import {UserService} from './shared/user.service';
import {App} from './app.component';

describe('App', () => {
    // provide our implementations or mocks to the dependency injector
    beforeEachProviders(() => [
        App,
        UserService,
        BaseRequestOptions,
        MockBackend,
        // Provide a mocked (fake) backend for Http
        provide(Http, {
        useFactory: function useFactory(backend, defaultOptions) {
            return new Http(backend, defaultOptions);
        },
        deps: [MockBackend, BaseRequestOptions]
        })
    ]);

    it('', inject([App], (app: App) => {
    }));
});
