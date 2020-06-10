
/**
 * Module principal de l'application
 * Pour rappel, un module permet de représenter un lot de fonctionnalités. Il va référencer les composants de votre applications
 * il aura également pour responsabilité d'appeler le composant racine (bootstrap component) qui va être à l'origine de tous les composants
 * Ici, on parle de "AppComponent"
 */
import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { HomeComponent } from './home/home.component';
import { LoginComponent } from './login/login.component';
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MaterialModule } from './shared/modules/material/material.module';
import { FlexLayoutModule } from '@angular/flex-layout';
import { DashboardComponent } from './home/dashboard/dashboard.component';
import { UserComponent } from './home/user/user.component';
import { UsersListComponent } from './home/users-list/users-list.component';
import { TokenHttpInterceptorService } from './shared/interceptors/token-http-interceptor.service';
import { PasswordConfirmationComponent } from './password-confirmation/password-confirmation.component';
import { PasswordForgottenComponent } from './password-forgotten/password-forgotten.component';
import { FooterComponent } from './home/dashboard/footer/footer.component';
import { ChatComponent } from './home/dashboard/chat/chat.component';
import { FilActualitesComponent } from './home/dashboard/fil-actualites/fil-actualites.component';
import { HeaderComponent } from './home/dashboard/header/header.component';
import { MenuSideBarComponent } from './home/menu-side-bar/menu-side-bar.component';
import { ProfilePictureUploader } from './home/profile-picture-uploader/profile-picture-uploader';
import { ProfileConfComponent } from './home/profile-conf/profile-conf.component';
import { UserConfComponent } from './home/profile-conf/user-conf/user-conf.component';
import { DetailsUserComponent } from './home/user/details-user/details-user.component';
import { SubscriptComponent } from './home/user/subscript/subscript.component';
import { QualificationsComponent } from './home/user/qualifications/qualifications.component';
import { JobApplicationsList } from './home/profile-conf/job-applications-list/job-applications-list.component';
import { SkillsComponent } from './home/user/skills/skills.component';
import { FormationsAssocieesComponent } from './home/offre/formations-associees/formations-associees.component';
import { ExigencesComponent } from './home/offre/exigences/exigences.component';
import { CandidaterComponent } from './home/offre/candidater/candidater.component';
import { InformationsComponent } from './home/offre/informations/informations.component';
import { OffreComponent } from './home/offre/offre.component';
import { FormationListComponent } from './home/formation-list/formation-list.component';
import { JobOfferListComponent } from './home/job-offer-list/job-offer-list.component';
import { MatPaginatorIntl } from '@angular/material/paginator';
import { getFrenchPaginatorIntl } from './shared/utils/customMatPaginationIntl';
import { MAT_RADIO_DEFAULT_OPTIONS } from '@angular/material/radio';
import { PageDataComponent } from './shared/utils/page-data/page-data.component';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatNativeDateModule } from '@angular/material/core';
import { NewPasswordComponent } from './home/profile-conf/new-password/new-password.component';
import { MatDialogModule } from '@angular/material/dialog';
import { ExistingAccountDialog } from './login/existing-account-dialog/existing-account-dialog.component';
import { ChipConfComponent } from './home/profile-conf/chip-conf/chip-conf.component';
import { ComponentModule } from './shared/components/component.module';

@NgModule({
    declarations: [ // Chaque composant que vous créez doit être déclaré ici
        AppComponent,
        HomeComponent,
        LoginComponent,
        DashboardComponent,
        UserComponent,
        UsersListComponent,
        PasswordConfirmationComponent,
        PasswordForgottenComponent,
        FooterComponent,
        HeaderComponent,
        ChatComponent,
        FooterComponent,
        FilActualitesComponent,
        MenuSideBarComponent,
        ProfilePictureUploader,
        ProfileConfComponent,
        UserConfComponent,
        DetailsUserComponent,
        SubscriptComponent,
        QualificationsComponent,
        JobApplicationsList,
        SkillsComponent,
        FormationsAssocieesComponent,
        ExigencesComponent,
        CandidaterComponent,
        InformationsComponent,
        OffreComponent,
        FormationListComponent,
        JobOfferListComponent,
        NewPasswordComponent,
        PageDataComponent,
        ExistingAccountDialog,
        ChipConfComponent,
    ],

    imports: [
        ComponentModule,
        BrowserModule,
        AppRoutingModule, // Toutes nos routes sont définies dans ce module
        BrowserAnimationsModule, // Nécessaire pour les animations Angular Material
        HttpClientModule, // Requit pour injecter la D.I. HttpClient qui nous permettra de requêter un serveur distant
        FormsModule, // Permet d'appliquer [(ngModel)] aux inputs
        ReactiveFormsModule, // Va nous permettre de créer des Model Driven Forms
        MaterialModule, // Ce module que nous avons créé contient l'ensemble des modules graphiques material à utiliser dans le projet
        FlexLayoutModule // Permet de positionner à l'aide des fxFlex, fxLayout, fxLayoutAlign etc.     
    ],

    entryComponents: [
        ExistingAccountDialog
    ],

    providers: [
        // Mise en place d'un intercepteur qui permettra d'appliquer le token automatiquement
        // à chaque requête sortante de notre application Angular
        // Le token correspond à notre "badge d'identité", il permet d'indiquer au serveur qui nous sommes
        // L'intercepteur va d'abord chercher à déterminer si le token est bien enregistré en localStorage ou non
        // Si oui, il l'envoie, sinon, il ne fait rien
        {
            provide: HTTP_INTERCEPTORS,
            useClass: TokenHttpInterceptorService,
            multi: true
        },
        { provide: MatPaginatorIntl, useValue: getFrenchPaginatorIntl() }, {
            provide: MAT_RADIO_DEFAULT_OPTIONS,
            useValue: { color: 'primary' },
        },
        MatDatepickerModule,
        MatNativeDateModule
    ],
    bootstrap: [AppComponent]
})
export class AppModule {
}
